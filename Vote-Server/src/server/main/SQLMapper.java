package server.main;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.domain.SurveyDTO;
import org.mybatis.domain.UserDTO;
import org.mybatis.domain.UserSurveyDTO;

public class SQLMapper {
	
	private static final String resource = "resources/mybatis/config-mybatis.xml";
	private static final String parameter = "org.mybatis.persistence.";
	
	private static SqlSessionFactory sqlSessionFactory;
	private static SqlSession sqlSession;
	
	/* MyBatis Setting */
	public SQLMapper() {
		
		try {
			/* MyBatis Option XML File Address */
			Reader reader = Resources.getResourceAsReader(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch(IOException e) {
			System.out.println("MyBatis Setting Err -> " + e.getMessage());
		}
		
	}
	
	/* Transaction Open */
	public void transactionOpen() {
		sqlSession = sqlSessionFactory.openSession();
	}
	
	/* Transaction Close */
	public void transactionClose() {
		sqlSession.close();
	}
	
	/* SQL Commit */
	public void sqlCommit() {
		sqlSession.commit();
	}
	
	/* SQL Roll Back */
	public void sqlRollBack() {
		sqlSession.rollback();
	}
	
	/* DB(Vote) Table Setting */
	public void sqlCreateTable() {
		
		try {
			transactionOpen();
			sqlSession.update(parameter + "TableMapper.createUserTable");
			sqlSession.update(parameter + "TableMapper.createSurveyTable");
			sqlSession.update(parameter + "TableMapper.createUserSurvey");
			transactionClose();
		} catch(Exception e) {
			System.out.println("Create Table Err -> " + e.getMessage());
		}
		
	}
	
	/* Survey List Count */
	public int sqlCountSurveyList() throws Exception {
		return sqlSession.selectOne(parameter + "ManagerMapper.countSurveyList");
	}
	
	/* Survey Table Data Insert */
	public void sqlInsertSurvey(SurveyDTO _survey) {
		try {
			sqlSession.insert(parameter + "ManagerMapper.insertSurvey", _survey);
		} catch(Exception e) {
			System.out.println("SQLMapper Survey Insert Err -> " + e.getMessage());
		}
	}
	
	/* UserInfo Table User Insert */
	public int sqlInsertUser(UserDTO _userDTO) {
		
		int result = 0;
		
		try {
			result = sqlSession.insert(parameter + "ManagerMapper.insertUser", _userDTO);
		} catch(Exception e) {
			System.out.println("SQLMapper User Insert Err -> " + e.getMessage());
		}
		
		return result;
	}
	
	/* UserInfo Table User Survey Insert */
	public int sqlInsertUserSurvey(UserSurveyDTO _userSurveyDTO) {
		
		int result = 0;
		
		try {
			result = sqlSession.insert(parameter + "ManagerMapper.insertUserSurvey", _userSurveyDTO);
		} catch(Exception e) {
			System.out.println("SQLMapper User Insert Err -> " + e.getMessage());
		}
		
		return result;
	}
	
	public int sqlUserCountCheck(UserDTO _userDTO) {
		
		int count = 0;
		
		try {
			count = sqlSession.selectOne(parameter + "ManagerMapper.countUserCheck", _userDTO);
			System.out.println(count);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return count;
		
	}
	
	public int sqlSelectUserLogin(UserDTO _userDTO) {
		
		int result = 0;
		
		try {
			//selectUserLogin
			
			result = sqlSession.selectOne(parameter + "ManagerMapper.selectUserLogin", _userDTO);
		} catch(Exception e) {
			//e.printStackTrace();
			result = 0;
		}
		System.out.println(result);
		return result;
		
	}
	
	/* Survey List Return */
	public List<SurveyDTO> sqlSelectSurveyList(List<SurveyDTO> _list) {
		try {
			_list = sqlSession.selectList(parameter + "ManagerMapper.selectSurveyList");
		} catch(Exception e) {
			System.out.println("SQLMapper Survey Select Err -> " + e.getMessage());
		}
		return _list;
	}
	
	/* Survey Activation Return */
	public List<SurveyDTO> sqlSelectSurveyActivation(List<SurveyDTO> _list) {
		try {
			_list = sqlSession.selectList(parameter + "ManagerMapper.seletSurveyRandom");
		} catch(Exception e) {
			System.out.println("SQLMapper Survey Select Err -> " + e.getMessage());
		}
		return _list;
	}
	
	/* Survey Data Update */
	public void sqlUpdateSurvey(SurveyDTO _survey) {
		try {
			sqlSession.update(parameter + "ManagerMapper.updateSurvey", _survey);
		} catch(Exception e) {
			System.out.println("SQLMapper Survey Update Err -> " + e.getMessage());
		}
	}
	
	public void sqlUdpateSurveyActivation(SurveyDTO _survey) {
		try {
			sqlSession.update(parameter + "ManagerMapper.updateSurveyActivation", _survey);
		} catch(Exception e) {
			System.out.println("SQLMapper Survey Activate Update Err -> " + e.getMessage());
		}
	}
	
	/* Survey Data Delete */
	public void sqlDeleteSurvey(String _surveyTitle) {
		try {
			sqlSession.delete(parameter + "ManagerMapper.deleteSurvey", _surveyTitle);
		} catch(Exception e) {
			System.out.println("SQLMapper Survey Update Err -> " + e.getMessage());
		}
	}
	
}
