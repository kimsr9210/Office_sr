package kr.or.houroffice.main.model.vo;

import java.sql.Timestamp;

public class CompanyRule {
	private int ruleNo;
	private String ruleTitle;
	private String ruleContent;
	private Timestamp ruleDate;
	private String ruleDateFormat;
	
	public CompanyRule() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CompanyRule(int ruleNo, String ruleTitle, String ruleContent, Timestamp ruleDate, String ruleDateFormat) {
		super();
		this.ruleNo = ruleNo;
		this.ruleTitle = ruleTitle;
		this.ruleContent = ruleContent;
		this.ruleDate = ruleDate;
		this.ruleDateFormat = ruleDateFormat;
	}
	
	public int getRuleNo() {
		return ruleNo;
	}
	public void setRuleNo(int ruleNo) {
		this.ruleNo = ruleNo;
	}
	public String getRuleTitle() {
		return ruleTitle;
	}
	public void setRuleTitle(String ruleTitle) {
		this.ruleTitle = ruleTitle;
	}
	public String getRuleContent() {
		return ruleContent;
	}
	public void setRuleContent(String ruleContent) {
		this.ruleContent = ruleContent;
	}
	public Timestamp getRuleDate() {
		return ruleDate;
	}
	public void setRuleDate(Timestamp ruleDate) {
		this.ruleDate = ruleDate;
	}
	public String getRuleDateFormat() {
		return ruleDateFormat;
	}
	public void setRuleDateFormat(String ruleDateFormat) {
		this.ruleDateFormat = ruleDateFormat;
	}
}
