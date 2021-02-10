package kr.or.houroffice.approval.model.vo;

import java.sql.Date;

public class AprFormHol extends AprForm{//form에서 정보 받아오기 & form에 정보 뿌려주기
	//양식별 정보
	private char holType;
	private Date startDate;
	private Date endDate;
	private char afternoonOff;
	private String reasons;
	private double countDay;
	
	public AprFormHol() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AprFormHol(int docuNo, String title, char lockYN, char urgencyYN, int[] aprLine, int[] aprRef, char aprType,
			int memNo, String memName, String deptCode, String deptName, Date docuDate, char docuType) {
		super(docuNo, title, lockYN, urgencyYN, aprLine, aprRef, aprType, memNo, memName, deptCode, deptName, docuDate,
				docuType);
		// TODO Auto-generated constructor stub
	}

	public AprFormHol(char holType, Date startDate, Date endDate, char afternoonOff, String reasons, double countDay) {
		super();
		this.holType = holType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.afternoonOff = afternoonOff;
		this.reasons = reasons;
		this.countDay = countDay;
	}
	public char getHolType() {
		return holType;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public char getAfternoonOff() {
		return afternoonOff;
	}
	public String getReasons() {
		return reasons;
	}
	public double getCountDay() {
		return countDay;
	}
	public void setHolType(char holType) {
		this.holType = holType;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setAfternoonOff(char afternoonOff) {
		this.afternoonOff = afternoonOff;
	}
	public void setReasons(String reasons) {
		this.reasons = reasons;
	}
	public void setCountDay(double countDay) {
		this.countDay = countDay;
	}
	
}
