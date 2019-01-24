package org.mybatis.domain;

public class UserSurveyDTO {
	
	private int number;
	private String userID;
	private String surveyTitle;
	private String vote;
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getSurveyTitle() {
		return surveyTitle;
	}
	public void setSurveyTitle(String surveyTitle) {
		this.surveyTitle = surveyTitle;
	}
	public String getVote() {
		return vote;
	}
	public void setVote(String vote) {
		this.vote = vote;
	}
	
	@Override
	public String toString() {
		return "UserSurveyDTO [number=" + number + ", userID=" + userID + ", surveyTitle=" + surveyTitle + ", vote="
				+ vote + "]";
	}
	
}
