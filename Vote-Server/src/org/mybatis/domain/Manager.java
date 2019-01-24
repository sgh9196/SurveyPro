package org.mybatis.domain;

public interface Manager {
	
	public final String ID = "admin";
	public final String PW = "root";
	
	public boolean managerLogin(String _ID, String _PW);
	
}
