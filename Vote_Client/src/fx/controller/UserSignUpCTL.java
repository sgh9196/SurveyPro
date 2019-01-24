package fx.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.client.domain.UserDTO;

import client.main.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UserSignUpCTL implements Initializable {
	
	@FXML private TextField SU_txtName;
	@FXML private TextField SU_txtID;
	@FXML private TextField SU_txtPW;
	@FXML private Button SU_btnSave;
	@FXML private Button SU_btnReSet;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		SU_btnSave.setOnAction(event -> handleBtnSaveAction(event));
		SU_btnReSet.setOnAction(event -> handleBtnReSetAction(event));
	}
	
	public void handleBtnReSetAction(ActionEvent _event) {
		SU_txtName.clear(); SU_txtID.clear(); SU_txtPW.clear();
	}
	
	public void handleBtnSaveAction(ActionEvent _event ) {
		
		UserInfo userInfo = new UserInfo();
		UserDTO userDTO = userInfo.getUserDTO(SU_txtName.getText(), SU_txtID.getText(), SU_txtPW.getText());
	
		userInfo.userSignUp(userDTO);
		
	}
	
}