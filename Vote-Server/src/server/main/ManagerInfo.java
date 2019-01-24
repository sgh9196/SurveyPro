package server.main;

import java.util.List;

import org.mybatis.domain.Manager;
import org.mybatis.domain.SurveyDTO;
import org.mybatis.domain.UserDTO;

import fx.controller.ServerAlertCTL;

public class ManagerInfo extends ServerAlertCTL implements Manager {
	
	private SQLMapper sqlMapper;
	
	public ManagerInfo() {
		sqlMapper = new SQLMapper();
	}
	
	@Override
	public boolean managerLogin(String _ID, String _PW) {
		
		boolean bool = false;
		
		try {
			if(_ID.equals(ID) && _PW.equals(PW)) {
				informationDialog("** [" + _ID + "] 님 접속 **");
				bool = true;
			}
			else { warningDialog("존재하지 않는 사용자입니다."); }
		} catch(Exception e) {
			System.out.println("ManagerInfo Login Err -> " + e.getMessage());
		}
		
		return bool;
	}
	
	/* SQL Commit Dialog Call */
	public boolean callCommitDialog() {

		boolean bool = false;
		
		if(commitDialog("확정 지으시겠습니까?")) {
			sqlMapper.sqlCommit();
			informationDialog("확정 되었습니다.");
			bool = true;
		}
		else { warningDialog("취소 되었습니다."); }
		
		return bool;
		
	}
	
	// ================================================================================
	
	/* Survey List Save and Return */
	public List<SurveyDTO> getSurveyList() {
		
		List<SurveyDTO> surveyList = null;
		
		sqlMapper.transactionOpen();
		surveyList = sqlMapper.sqlSelectSurveyList(surveyList);
		sqlMapper.transactionClose();
		
		return surveyList;
		
	}
	
	/* Survey List Save Activation and Return */
	public List<SurveyDTO> getSurveyActivationList() {
		
		List<SurveyDTO> surveyList = null;
		
		sqlMapper.transactionOpen();
		surveyList = sqlMapper.sqlSelectSurveyActivation(surveyList);
		sqlMapper.transactionClose();
		
		return surveyList;
		
	}
	
	/* Survey Information Object Return */
	public SurveyDTO getSurveyDTO(String data, List<SurveyDTO> _survey) {
		
		SurveyDTO surveyDTO = null;
		
		try {
			for(SurveyDTO list : _survey) {
				if(data.equals(list.getSurveyTitle())) {
					surveyDTO = list; 
					break;
				}
			}
		} catch(NullPointerException n) {
			System.out.println("ManagerInfo getSurveyDTO err -> " + n.getMessage());
		}
		return surveyDTO;
		
	}
	
	// ==================================================================================================
	
	/* Survey List Insert Mapping */
	public int surveyListInsert(SurveyDTO _survey) {

		int sqlResult = 0;
		
		try {
			sqlMapper.transactionOpen();
			_survey.setNumber(sqlMapper.sqlCountSurveyList() + 1);
			sqlMapper.sqlInsertSurvey(_survey);
			if(callCommitDialog()) { sqlResult = 1; }
		} catch(Exception e) {
			System.out.println("ManagerInfo Survey Insert Err -> " + e.getMessage());
		} finally {
			sqlMapper.transactionClose();
		}
		return sqlResult;
		
	}

	/* Survey Data Update Mapping */
	public int surveyDataUpdate(SurveyDTO _survey) {
		
		int sqlResult = 0;
		
		try {
			sqlMapper.transactionOpen();
			sqlMapper.sqlUpdateSurvey(_survey);
			if(callCommitDialog()) { sqlResult = 1; }
		} catch(Exception e) {
			System.out.println("ManagerInfo Survey Update Err -> " + e.getMessage());
		} finally {
			sqlMapper.transactionClose();
		}
		return sqlResult;
	}
	
	/* Survey Activation Update */
	public void surveyActivationUpdate(SurveyDTO _survey) {
		
		try {
			sqlMapper.transactionOpen();
			sqlMapper.sqlUdpateSurveyActivation(_survey);
			sqlMapper.sqlCommit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			sqlMapper.transactionClose();
		}
		
		
	}
	
	/* Survey Data Delete Mapping */
	public int SurveyDataDelete(String _surveyTitle) {
		
		int sqlResult = 0;
		
		try {
			sqlMapper.transactionOpen();
			sqlMapper.sqlDeleteSurvey(_surveyTitle);
			if(callCommitDialog()) { sqlResult = 1; }
		} catch(Exception e) {
			System.out.println("ManagerInfo Survey Delete Err -> " + e.getMessage());
		} finally {
			sqlMapper.transactionClose();
		}
		return sqlResult;
	}
	
	// ==================================================================================================
	
	/* Server Open And Close */
	public boolean managerServerActivation(String _ip, String _port) {
		
		boolean bool = false;
		String msg = "";
		
		if(_ip.equals("") && _port.equals("")) { msg = "IP와 PORT의 정보를 입력하세요."; }
		else if(_ip.equals("")) { msg = "IP 정보를 입력하세요."; }
		else if(_port.equals("")) { msg = "PORT 정보를 입력하세요"; }
		else { bool = true; }
		
		if(bool) { informationDialog("정상 처리 되었습니다,"); }
		else { warningDialog(msg); }
		
		return bool;
		
	}

	
}

