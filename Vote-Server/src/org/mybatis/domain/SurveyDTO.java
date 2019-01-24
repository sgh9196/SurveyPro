package org.mybatis.domain;

public class SurveyDTO {
	
	private int number = 0;
	private String surveyTitle = "";
	private String vote_1 = "";
	private String vote_2 = "";
	private String vote_3 = "";
	private String vote_4 = "";
	private String vote_5 = "";
	private String vote_6 = "";
	private String vote_7 = "";
	private String vote_8 = "";
	private String vote_9 = "";
	private String vote_10 = "";
	private boolean activation = false;
	
	
	public SurveyDTO(int _number, String _surveyTitle, String _vote1, String _vote2, String _vote3,
						String _vote4, String _vote5, String _vote6, String _vote7, 
						String _vote8, String _vote9, String _vote10, boolean _activation) {

		this.number = _number;
		this.surveyTitle = _surveyTitle;
		this.vote_1 = _vote1;
		this.vote_2 = _vote2;
		this.vote_3 = _vote3;
		this.vote_4 = _vote4;
		this.vote_5 = _vote5;
		this.vote_6 = _vote6;
		this.vote_7 = _vote7;
		this.vote_8 = _vote8;
		this.vote_9 = _vote9;
		this.vote_10 = _vote10;
		this.activation = _activation;
		
	}

	public int getNumber() { return number; }
	public void setNumber(int number) { this.number = number; }

	public String getSurveyTitle() { return surveyTitle; }
	public void setSurveyTitle(String surveyTitle) { this.surveyTitle = surveyTitle; }
	
	public String getVote_1() { return vote_1; }
	public void setVote_1(String vote_1) { this.vote_1 = vote_1; }
	
	public String getVote_2() { return vote_2; }
	public void setVote_2(String vote_2) { this.vote_2 = vote_2; }
	
	public String getVote_3() { return vote_3; }
	public void setVote_3(String vote_3) { this.vote_3 = vote_3; }
	
	public String getVote_4() { return vote_4; }
	public void setVote_4(String vote_4) { this.vote_4 = vote_4; }
	
	public String getVote_5() { return vote_5; }
	public void setVote_5(String vote_5) { this.vote_5 = vote_5; }
	
	public String getVote_6() { return vote_6; }
	public void setVote_6(String vote_6) { this.vote_6 = vote_6; }
	
	public String getVote_7() { return vote_7; }
	public void setVote_7(String vote_7) { this.vote_7 = vote_7; }
	
	public String getVote_8() { return vote_8; }
	public void setVote_8(String vote_8) { this.vote_8 = vote_8; }
	
	public String getVote_9() { return vote_9; }
	public void setVote_9(String vote_9) { this.vote_9 = vote_9; }
	
	public String getVote_10() { return vote_10; }
	public void setVote_10(String vote_10) { this.vote_10 = vote_10; }

	public boolean isActivation() { return activation; }
	public void setActivation(boolean activation) { this.activation = activation; }

	@Override
	public String toString() {
		return "SurveyDTO [number=" + number + ", surveyTitle=" + surveyTitle + ", vote_1=" + vote_1 + ", vote_2="
				+ vote_2 + ", vote_3=" + vote_3 + ", vote_4=" + vote_4 + ", vote_5=" + vote_5 + ", vote_6=" + vote_6
				+ ", vote_7=" + vote_7 + ", vote_8=" + vote_8 + ", vote_9=" + vote_9 + ", vote_10=" + vote_10
				+ ", activation=" + activation + "]";
	}

}
