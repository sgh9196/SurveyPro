package fx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import server.main.ManagerInfo;

public class ServerLoginCTL extends ServerAlertCTL implements Initializable {

	@FXML private TextField textID;
	@FXML private PasswordField textPassWD;
	
	@FXML private Button btnLogin;
	
	// ==========================================================================
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnLogin.setOnAction(event -> handle_M_btnLoginAction(event));
	}

	// ==========================================================================
	
	/* Next Stage Show */
	public void showStage(String _fxml) throws IOException {
		
		Parent p = FXMLLoader.load(getClass().getResource(_fxml));
		Stage stage = new Stage();
		
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setTitle("Manager");
		stage.setResizable(false);
		stage.setScene(new Scene(p));
		stage.show();
		
	}
	
	// ==========================================================================
	
	/* Manager Login (Button - Login) */
	public void handle_M_btnLoginAction(ActionEvent _event) {
		
		ManagerInfo mInfo = new ManagerInfo();
		
		String id = textID.getText();
		String pw = textPassWD.getText();
		
		try {
			
			boolean loginTmp = mInfo.managerLogin(id, pw);
			
			if(loginTmp) showStage("../../formManager.fxml");
			
		} catch(IOException e) {
			System.out.println("LoginCTL(btnLogin) Err -> " + e.getMessage());
		}
		
	}
	
}
