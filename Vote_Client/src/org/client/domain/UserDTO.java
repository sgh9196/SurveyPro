package org.client.domain;

public class UserDTO {
	
	private String userName;
	private String userPhone;
	private String userPassWD;
	
	public UserDTO(String _userName, String _userPhone, String _userPassWD) {
		this.userName = _userName;
		this.userPhone = _userPhone;
		this.userPassWD = _userPassWD;
	}
	
	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }
	
	public String getUserPhone() { return userPhone; }
	public void setUserPhone(String userPhone) { this.userPhone = userPhone; }
	
	public String getUserPassWD() { return userPassWD; }
	public void setUserPassWD(String userPassWD) { this.userPassWD = userPassWD; }
	
	@Override
	public String toString() {
		return "UserDTO [userName=" + userName + ", userPhone=" + userPhone + ", userPassWD=" + userPassWD + "]";
	}
	
}
