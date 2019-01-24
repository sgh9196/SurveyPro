package client.main;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

public class ClientAlertCTL {
	
	/* COMMIT Dialog (YES, NO) */
	public boolean commitDialog(String _msg) {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("COMMIT");
		alert.setHeaderText(_msg);
		
		Optional<ButtonType> result = alert.showAndWait();

		return result.get()==ButtonType.OK;
		
	}
	
	/* WARNING Dialog (Yes) */
	public void warningDialog(String _msg) {
		
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("WARNING");
		alert.setHeaderText(_msg);
		
		alert.showAndWait();
		
	}
	
	/* INFORMATION Dialog (Yes) */
	public void informationDialog(String _msg) {
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("INFORMATION");
		alert.setHeaderText(_msg);
		
		alert.showAndWait();
		
	}
	
	/* TEXT INPUT Dialog (Yes, No) */
	public String textinputDialog() {
		
		TextInputDialog dialog = new TextInputDialog();
		
		dialog.setTitle("설문조사");
		dialog.setHeaderText("조사 할 설문을 입력해주세요.");
		
		Optional<String> result = dialog.showAndWait();
		
		return result.get();
		
	}
	
}
