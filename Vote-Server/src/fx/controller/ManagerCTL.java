package fx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

import org.mybatis.domain.SurveyDTO;

import MultiServer.TcpSingleServer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import server.main.FileManagement;
import server.main.ManagerInfo;

public class ManagerCTL extends ServerAlertCTL implements Initializable {
	
	@FXML private AnchorPane mainPane;
	
	@FXML private TitledPane titlePaneVote;
	@FXML private TitledPane titlePaneServer;
	
	@FXML private ListView<String> listVoteView;
	@FXML private ListView<String> listClientView;
	
	@FXML private TextField textVote1;
	@FXML private TextField textVote2;
	@FXML private TextField textVote3;
	@FXML private TextField textVote4;
	@FXML private TextField textVote5;
	@FXML private TextField textVote6;
	@FXML private TextField textVote7;
	@FXML private TextField textVote8;
	@FXML private TextField textVote9;
	@FXML private TextField textVote10;
	
	@FXML private TextField textIP;
	@FXML private TextField textPort;
	
	@FXML private Button btnSurveyTitle;
	@FXML private Button btnClear;
	@FXML private Button btnAdd;
	@FXML private Button btnUpdate;
	@FXML private Button btnDelete;
	@FXML private Button btnActivation;
	
	@FXML private Button btnOpen;
	@FXML private Button btnClose;

	@FXML private TextArea textProgress;
	
	// =================================================================================================================
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		voteListViewUpdate();
		
		btnClear.setOnAction(event -> voteTextFieldClear());
		btnSurveyTitle.setOnAction(event -> handleBtnSurveyTitle());
		btnAdd.setOnAction(event -> handleBtnAddAction(event));
		listVoteView.setOnMouseClicked(event -> handleListVoteClikedAction(event));
		btnUpdate.setOnAction(event -> handleBtnUpdateAction(event));
		btnDelete.setOnAction(event -> handleBtnDeleteAction(event));
		btnActivation.setOnAction(event -> handleBtnActivationAction(event));
		btnOpen.setOnAction(event -> handleBtnOpenAction(event));
		btnClose.setOnAction(event -> handleBtnCloseAction(event));
		
	}

	/* SurveyDTO Object Return */
	public SurveyDTO setSurveyDTO() {
		SurveyDTO survey = new SurveyDTO(0, titlePaneVote.getText(), textVote1.getText(), textVote2.getText(), textVote3.getText(), 
				textVote4.getText(), textVote5.getText(), textVote6.getText(), textVote7.getText(), 
				textVote8.getText(), textVote9.getText(), textVote10.getText(), false);
		
		return survey;
	}
	
	/* Vote 1~10 TextField Clear */
	public void voteTextFieldClear() {
		titlePaneVote.setText("Survey Title");
		textVote1.clear(); textVote2.clear(); textVote3.clear(); textVote4.clear();
		textVote5.clear(); textVote6.clear(); textVote7.clear(); textVote8.clear();
		textVote9.clear(); textVote10.clear();
		listVoteView.getSelectionModel().select(-1);
	}
	
	/* Survey List View Update */
	public void voteListViewUpdate() {
		
		listVoteView.getItems().clear();
		List<SurveyDTO> surveyList = new ManagerInfo().getSurveyList();
		for(SurveyDTO list : surveyList) {
			listVoteView.getItems().add(list.getSurveyTitle());
			if(list.isActivation()) { btnActivation.setText("Activation → On"); }
			else { btnActivation.setText("Activation → Off"); }
		}
	
	}
	
	// =================================================================================================================
	
	/* Button SurveyTitle Action Event */
	public void handleBtnSurveyTitle() {
		try {
			ServerAlertCTL alert = new ServerAlertCTL();
			String surveyTitle = alert.textinputDialog();
			titlePaneVote.setText(surveyTitle);
		} catch(NoSuchElementException n) { }
	}
	
	/* Button Add Action Event */
	public void handleBtnAddAction(ActionEvent _event) {
		
		String surveyTitle = titlePaneVote.getText();
		
		if(!surveyTitle.equals("Survey Title")) {
			int eResult = new ManagerInfo().surveyListInsert(setSurveyDTO());
			if(eResult==1) {
				listVoteView.getItems().add(surveyTitle);
				voteTextFieldClear();
			}
		}
		
	}
	
	/* List Vote View Clicked Event */
	public void handleListVoteClikedAction(MouseEvent _event) {
		
		List<SurveyDTO> surveyList = new ManagerInfo().getSurveyList();
		String clickList = listVoteView.getSelectionModel().getSelectedItem();
		
		SurveyDTO survey = new ManagerInfo().getSurveyDTO(clickList, surveyList);
		
		titlePaneVote.setText(survey.getSurveyTitle());
		textVote1.setText(survey.getVote_1());
		textVote2.setText(survey.getVote_2());
		textVote3.setText(survey.getVote_3());
		textVote4.setText(survey.getVote_4());
		textVote5.setText(survey.getVote_5());
		textVote6.setText(survey.getVote_6());
		textVote7.setText(survey.getVote_7());
		textVote8.setText(survey.getVote_8());
		textVote9.setText(survey.getVote_9());
		textVote10.setText(survey.getVote_10());
		
		if(!survey.isActivation()) { btnActivation.setText("Activation → On"); }
		else { btnActivation.setText("Activation → Off"); }
		
	}
	
	/* Button Update Action Event */
	public void handleBtnUpdateAction(ActionEvent _event) {
		
		SurveyDTO survey = setSurveyDTO();
		
		if(!survey.getSurveyTitle().equals("Survey Title")) {
			int eResult = new ManagerInfo().surveyDataUpdate(survey);
			if(eResult==1) { voteTextFieldClear(); }
		}
		
	}
	
	/* Button Delete Action Event */
	public void handleBtnDeleteAction(ActionEvent _event) {
		
		String surveyTitle = listVoteView.getSelectionModel().getSelectedItem();
		int eResult = new ManagerInfo().SurveyDataDelete(surveyTitle);
		if(eResult==1) {
			listVoteView.getItems().remove(surveyTitle);
			voteTextFieldClear(); 
		}
		
	}
	
	/* Button Update Activation Event */
	public void handleBtnActivationAction(ActionEvent _event) {
		
		ManagerInfo managerInfo = new ManagerInfo();
		
		List<SurveyDTO> surveyList = managerInfo.getSurveyList();
		String clickList = listVoteView.getSelectionModel().getSelectedItem();
		
		SurveyDTO survey = managerInfo.getSurveyDTO(clickList, surveyList);
		
		if(listVoteView.getSelectionModel().getSelectedIndex() >= 0) {
			if(survey.isActivation()) {
				survey.setActivation(false);
				btnActivation.setText("Activation → On");	
			}
			else {
				survey.setActivation(true);
				btnActivation.setText("Activation → Off");
			}
			managerInfo.surveyActivationUpdate(survey);
		}
		//((Stage)mainPane.getScene().getWindow()).close();
	}
	
	// =================================================================================================
	
	/* Button Open (Server) */
	public void handleBtnOpenAction(ActionEvent _event) {
		
		String ip = textIP.getText();
		String port = textPort.getText();
		try {
			boolean tmp = new ManagerInfo().managerServerActivation(ip, port);
			if(tmp) {
				new FileManagement().serverFileWrite(ip, port, "true");
				
				TcpSingleServer server = new TcpSingleServer(Integer.parseInt(port));
				server.setContorllObject(textProgress, listClientView);
				server.start();
			}
		} catch (IOException e) {
				e.printStackTrace();
		}

	}
	
	public void handleBtnCloseAction(ActionEvent _event) {
		new FileManagement().serverFileDelete();
		System.exit(0);
	}
	
}