package MultiServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.mybatis.domain.SurveyDTO;
import org.mybatis.domain.UserDTO;
import org.mybatis.domain.UserSurveyDTO;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import server.main.ManagerInfo;
import server.main.SQLMapper;

public class TcpSingleServer extends Thread {

	private HashMap<String, DataOutputStream> clientMap;

	private ListView<String> listClientView;
	private TextArea textProgress;

	private int Port;

	// ============================================================================================

	public TcpSingleServer(int _port) {
		this.Port = _port;
		clientMap = new HashMap<String, DataOutputStream>();
		Collections.synchronizedMap(clientMap);
	}

	/* Object Set */
	public void setContorllObject(TextArea _textProgress, ListView<String> _listClientView) {
		this.textProgress = _textProgress;
		this.listClientView = _listClientView;
	}

	// ============================================================================================

	@Override
	public void run() {

		ServerSocket serverSocket = null;
		Socket socket = null;

		try {

			serverSocket = new ServerSocket(Port);
			this.textProgress.appendText("Manager -> Server Open\n");

			while (true) {

				socket = serverSocket.accept();
				textProgress.appendText(socket.getInetAddress() + " -> " + socket.getPort() + "\n");

				Thread rec = new TcpSingleServerRec(socket);
				rec.start();
			}

		} catch (Exception e) {
			System.out.println("TcpSingleServer run Err ->> " + e.getMessage());
		}

	}

	class TcpSingleServerRec extends Thread {

		private Socket socket;
		private DataInputStream in;
		private DataOutputStream out;

		private String userData = "";
		private String[] dataSet = null;
		private String userID = "";

		public TcpSingleServerRec(Socket _socket) {
			this.socket = _socket;
			try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
			} catch (Exception e) {
			}
		}

		private void cliendListViewAdd(String _userID) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					listClientView.getItems().add(_userID);
				}
			});

		}

		public String[] userDataSelection() {

			String[] dataSet = new String[4];
			int count = 0;

			for (int i = 0; i < dataSet.length; i++)
				dataSet[i] = "";

			for (int i = 0; i < userData.length(); i++) {

				String cData = String.valueOf(userData.charAt(i));
				count += (userData.charAt(i) == '|') ? 1 : 0;

				if (cData.equals("|"))
					continue;

				switch (count) {
				case 0:
					dataSet[0] += cData;
					break;
				case 1:
					dataSet[1] += cData;
					break;
				case 2:
					dataSet[2] += cData;
					break;
				case 3:
					dataSet[3] += cData;
					break;
				}

			}
			return dataSet;
		}

		/* User Sign Up */
		public void userSignUp(String... dataSet) {

			int result = 0;

			try {

				UserDTO userDTO = new UserDTO();
				userDTO.setUserName(dataSet[1]);
				userDTO.setUserPhone(dataSet[2]);
				userDTO.setUserPassWD(dataSet[3]);

				SQLMapper sqlMapper = new SQLMapper();
				sqlMapper.transactionOpen();

				if (sqlMapper.sqlUserCountCheck(userDTO) == 0) {
					result = sqlMapper.sqlInsertUser(userDTO);
					if (result != 0)
						sqlMapper.sqlCommit();
					sqlMapper.transactionClose();
				}
				out.writeUTF(String.valueOf(result));

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		/* User Login */
		public int userLogin(String... dataSet) {

			int result = 0;

			UserDTO userDTO = new UserDTO();
			userDTO.setUserPhone(dataSet[2]);
			userID = dataSet[2];
			userDTO.setUserPassWD(dataSet[3]);

			SQLMapper sqlMapper = new SQLMapper();

			sqlMapper.transactionOpen();
			result = sqlMapper.sqlSelectUserLogin(userDTO);
			// System.out.println(result);
			sqlMapper.transactionClose();

			return result;
		}

		public boolean clientMapCheck(String[] dataSet) {

			boolean tmp = false;

			Iterator<String> itr = clientMap.keySet().iterator();
			while (itr.hasNext()) {
				String userPhone = itr.next();
				if (dataSet[2].equals(userPhone)) {
					tmp = true;
					break;
				}
			}

			return tmp;

		}

		@Override
		public void run() {

			// dataSet[0] : 회원가입, 로그인 판단
			// dataSet[1] : User Name
			// dataSet[2] : User Phone
			// dataSet[3] : User Password

			try {
				
				SurveyDTO survey = null;
				
				while (in != null) {

					userData = in.readUTF();
					dataSet = userDataSelection(); // 데이터 자르기
					
					if (dataSet[0].equals("S")) {
						userSignUp(dataSet);
					} 
					else if (dataSet[0].equals("L")) {

						int result = userLogin(dataSet);

						if (!clientMapCheck(dataSet)) {
							out.writeUTF(String.valueOf(result));
							if (result != 0) {
								clientMap.put(dataSet[2], out);
								textProgress.appendText(dataSet[2] + "님이 서버와 연결되었습니다.\n");
								cliendListViewAdd(dataSet[2]);

								List<SurveyDTO> list = new ManagerInfo().getSurveyActivationList();
								survey = list.get(new Random().nextInt(list.size()));

								String data = survey.getSurveyTitle() + "|";
								data += survey.getVote_1() + "|";
								data += survey.getVote_2() + "|";
								data += survey.getVote_3() + "|";
								data += survey.getVote_4() + "|";
								data += survey.getVote_5() + "|";
								data += survey.getVote_6() + "|";
								data += survey.getVote_7() + "|";
								data += survey.getVote_8() + "|";
								data += survey.getVote_9() + "|";
								data += survey.getVote_10();

								out.writeUTF(data);
							}

						} else { out.writeUTF("0"); }
					}
					else {
						
						String usrVote = in.readUTF();
						
						SQLMapper sqlMapper = new SQLMapper();
						UserSurveyDTO usd = new UserSurveyDTO();
						usd.setUserID(userID);
						usd.setSurveyTitle(survey.getSurveyTitle());
						usd.setVote(usrVote);
						
						sqlMapper.transactionOpen();
						sqlMapper.sqlInsertUserSurvey(usd);
						sqlMapper.sqlCommit();
						sqlMapper.transactionClose();
				
						//sqlInsertUserSurvey
						
					}
				}
			} catch (Exception e) {
				//System.out.println("TcpSingle Server Rec run Err ->> " + e.getMessage());
				e.printStackTrace();
			} finally {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						clientMap.remove(dataSet[2]);
						textProgress.appendText("[" + dataSet[2] + "]님 퇴장\n");
						listClientView.getItems().remove(dataSet[2]);
					}
				});
			}

		}

	}

}
