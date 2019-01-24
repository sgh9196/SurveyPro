package client.main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;

import org.client.domain.UserDTO;

public class UserInfo extends ClientAlertCTL {
	
	public UserDTO getUserDTO(String _name, String _id, String _pw) {
		return new UserDTO(_name, _id, _pw);
	}
	
	/* Open Server Data Information getter */
	public String[] getServerFileData() {
		
		String[] data = new String[3];
		BufferedReader in = null;
		
		try {
			int index = 0;
			String fileLine = "";
			in = new BufferedReader(new FileReader("../data.txt"));
			
			while((fileLine=in.readLine())!=null) {
				data[index] = fileLine;
				index++;
			}
			in.close();
		} catch(FileNotFoundException fnE) {
			warningDialog("서버가 열려있지 않습니다. 다음에 다시 시도해주세요");
		} catch(IOException ioE) { }
		
		return data;
	}

	
	/* User Data Server Send*/
	public void userSignUp(UserDTO _userDTO) {
		
		Socket socket = null;
		DataOutputStream out = null;
		DataInputStream in = null;
		
		try {
			
			String[] data = getServerFileData();
			socket = new Socket(data[0], Integer.parseInt(data[1]));
			int serverResult = 0;
			
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			
			String userData = _userDTO.getUserName() + "|";
			userData += _userDTO.getUserPhone() + "|";
			userData += _userDTO.getUserPassWD();
			
			out.writeUTF("S|" + userData);

			serverResult = Integer.parseInt(in.readUTF());
			
			if(serverResult > 0) informationDialog("회원가입 성공");
			else warningDialog("회원가입 실패");
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close(); in.close(); out.close();
			} catch(Exception e) {}
		}
		
		
		
	}
	
}
