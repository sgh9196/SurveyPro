package fx.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import org.client.domain.UserDTO;

import client.main.ClientAlertCTL;
import client.main.UserInfo;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UserCTL implements Initializable {

	@FXML private AnchorPane loginPane;
	@FXML private TitledPane titlePaneVote;
	@FXML private GridPane resultPane;

	@FXML private TextField textID;
	@FXML private PasswordField textPassWD;
	
	@FXML private Button btnSignUp;
	@FXML private Button btnLogin;
	
	@FXML private RadioButton radioResult1;
	@FXML private RadioButton radioResult2;
	@FXML private RadioButton radioResult3;
	@FXML private RadioButton radioResult4;
	@FXML private RadioButton radioResult5;
	@FXML private RadioButton radioResult6;
	@FXML private RadioButton radioResult7;
	@FXML private RadioButton radioResult8;
	@FXML private RadioButton radioResult9;
	@FXML private RadioButton radioResult10;
	
	@FXML private Label l1;
	@FXML private Label l2;
	@FXML private Label l3;
	@FXML private Label l4;
	@FXML private Label l5;
	@FXML private Label l6;
	@FXML private Label l7;
	@FXML private Label l8;
	@FXML private Label l9;
	@FXML private Label l10;
	
	@FXML private ProgressBar p1;
	@FXML private ProgressBar p2;
	@FXML private ProgressBar p3;
	@FXML private ProgressBar p4;
	@FXML private ProgressBar p5;
	@FXML private ProgressBar p6;
	@FXML private ProgressBar p7;
	@FXML private ProgressBar p8;
	@FXML private ProgressBar p9;
	@FXML private ProgressBar p10;
	
	
	@FXML private Button btnSave;
	@FXML private Button btnResult;
	
	private String radioSelect = "";
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		btnLogin.setOnAction(event -> handleBtnLoginAction(event));
		btnSignUp.setOnAction(event -> handleBtnSignUpAction(event));
		serverPortConfirm();
		
		toggleGroup();
		
		
	}

	public String[] serverPortConfirm() {
		
		String[] data = new UserInfo().getServerFileData();
		
		if(data[0]==null) System.exit(0);
		
		return data;
		
	}
	
	/* Next Stage Show */
	public void showStage(String _fxml) throws IOException {
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				
				try {
				
					Parent p = FXMLLoader.load(getClass().getResource(_fxml));
					Stage stage = new Stage();
					
					stage.setTitle(textID.getText());
					stage.setResizable(false);
					stage.setScene(new Scene(p));
					stage.show();
				
				} catch(Exception e) {}
			}
		});

		
	}
	
	public void toggleGroup() {
		ToggleGroup group = new ToggleGroup();
		radioResult1.setToggleGroup(group);
		radioResult2.setToggleGroup(group);
		radioResult3.setToggleGroup(group);
		radioResult4.setToggleGroup(group);
		radioResult5.setToggleGroup(group);
		radioResult6.setToggleGroup(group);
		radioResult7.setToggleGroup(group);
		radioResult8.setToggleGroup(group);
		radioResult9.setToggleGroup(group);
		radioResult10.setToggleGroup(group);
		
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				if (group.getSelectedToggle() != null) {
					if (group.getSelectedToggle() != null) {
		                   RadioButton button = (RadioButton) group.getSelectedToggle();
		                   radioSelect = button.getText();
		               }
		         }
			}
			
		});
		
	}
	
	
	// ==================================================================
	
	/* Button Login Action Event */
	public void handleBtnLoginAction(ActionEvent _event) {
		
		UserDTO userDTO = new UserDTO(" ", textID.getText(), textPassWD.getText());
		String[] data = serverPortConfirm();
		
		try {
		
			String user = "L|";
			user += userDTO.getUserName() + "|";
			user += userDTO.getUserPhone() + "|";
			user += userDTO.getUserPassWD();
			
			Socket socket = new Socket(data[0], Integer.parseInt(data[1]));
			
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			
			out.writeUTF(user);
			
			boolean result = (Integer.parseInt(in.readUTF()) > 0) ? true : false;
			
			ClientAlertCTL alert = new ClientAlertCTL();
			
			if(!result) { alert.warningDialog("등록되지 않은 회원입니다."); }
			else {
				
				alert.informationDialog("로그인 되었습니다.");
				titlePaneVote.setVisible(true);
				loginPane.setVisible(false);
				TcpSingleReceiver tsr = new TcpSingleReceiver(socket);
				tsr.start();
			}
		
		} catch(Exception e) { e.printStackTrace(); }
		
	}

	/* Button SignUp Action Event */
	public void handleBtnSignUpAction(ActionEvent _event) {
		
		UserInfo userInfo = new UserInfo();
		String[] data = userInfo.getServerFileData();
		
		try {
			if(data[2].equals("true")) {
				showStage("../../formSignUp.fxml");
			}
		} catch(NullPointerException npE) { } catch(IOException e) { }
		
	}
	
	/* 내부 클레스 Sender */
	class TcpSingleSender extends Thread {
		
		private Socket socket;
		private DataOutputStream out;
		
		int count = 0;
		
		public TcpSingleSender(Socket _socket) {
			try {
				this.socket = _socket;
				out = new DataOutputStream(socket.getOutputStream());
			} catch(Exception e) { }
		}
		
		@Override
		public void run() {
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					btnSave.setOnAction(event -> {
						try {
							count++;
							out.writeUTF(radioSelect);
							if(count==2) {
								btnSave.setVisible(false);
							}
						} catch(Exception e) {}
					});
					
					btnResult.setOnAction(event -> {
						try {
							out.writeUTF("R");
							
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									try {
										
										titlePaneVote.setVisible(false);
										resultPane.setVisible(true);
										
										DataInputStream in = new DataInputStream(socket.getInputStream());
										String data = in.readUTF();
										String text = "";
										int count = 0;
										
										for(int i=0; i<data.length(); i++) {

											if(data.charAt(i) == '|') {
												if(text.equals(radioSelect)) { text += " *"; }
												switch(count) {
													case 1: l1.setText(text); break;
													case 2: l2.setText(text); break;
													case 3: l3.setText(text); break;
													case 4: l4.setText(text); break;
													case 5: l5.setText(text); break;
													case 6: l6.setText(text); break;
													case 7: l7.setText(text); break;
													case 8: l8.setText(text); break;
													case 9: l9.setText(text); break;
													case 10: l10.setText(text); break;
												}
												count++; text = "";
											}
											else {
												text += String.valueOf(data.charAt(i));
											}
											
										}
										
										String countData = in.readUTF();
										
										double[] count2 = new double[10];
										for(int i=0; i<count2.length; i++) 
											count2[i] = Double.parseDouble(String.valueOf(countData.charAt(i))) * 0.1;
										
										p1.setProgress(count2[0]);
										p2.setProgress(count2[1]);
										p3.setProgress(count2[2]);
										p4.setProgress(count2[3]);
										p5.setProgress(count2[4]);
										p6.setProgress(count2[5]);
										p7.setProgress(count2[6]);
										p8.setProgress(count2[7]);
										p9.setProgress(count2[8]);
										p10.setProgress(count2[9]);
										
									} catch(Exception e) {}
								}
							});
							
						} catch(Exception e) {}
					});
					
				}
			});
			
			
		}
		
	}
	
	
	
	/* 내부 클레스 Receiver */
	public class TcpSingleReceiver extends Thread {

		private Socket socket;
		private DataInputStream in;
		
		public TcpSingleReceiver(Socket _socket) {
			try {
				this.socket = _socket;
				in = new DataInputStream(socket.getInputStream());
			} catch(Exception e) { }
		}
		

		public void radioTextIn() {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					
					try {
						
						String data = in.readUTF();
						String text = "";
						int count = 0;
						
						for(int i=0; i<data.length(); i++) {

							if(data.charAt(i) == '|') {
								switch(count) {
									case 0: titlePaneVote.setText(text); break;
									case 1: radioResult1.setText(text); ; break;
									case 2: radioResult2.setText(text); break;
									case 3: radioResult3.setText(text); break;
									case 4: radioResult4.setText(text); break;
									case 5: radioResult5.setText(text); break;
									case 6: radioResult6.setText(text); break;
									case 7: radioResult7.setText(text); break;
									case 8: radioResult8.setText(text); break;
									case 9: radioResult9.setText(text); break;
									case 10: radioResult10.setText(text); break;
								}
								count++; text = "";
							}
							else {
								text += String.valueOf(data.charAt(i));
							}
							
						}
						
					} catch(Exception e) {
						
					}
				}
				
			
			});
		}
		
		@Override
		public void run() {

			radioTextIn();
			TcpSingleSender tss = new TcpSingleSender(socket);
			tss.start();
	
			
			
			
			
		
		}
	}
}
