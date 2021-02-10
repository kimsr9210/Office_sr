package kr.or.houroffice.approval.model.vo;

import java.sql.Date;

public class AprFormOvt extends AprForm{//form에서 정보 받아오기 & form에 정보 뿌려주기
	//양식 정보
	private char ovtType;
	private Date ovtDate;
	private int startHour;
	private int startMinute;
	private int endHour;
	private int endMinute;
	private int totalHour;
	private String reasons;
	public AprFormOvt() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AprFormOvt(int docuNo, String title, char lockYN, char urgencyYN, int[] aprLine, int[] aprRef, char aprType,
			int memNo, String memName, String deptCode, String deptName, Date docuDate, char docuType) {
		super(docuNo, title, lockYN, urgencyYN, aprLine, aprRef, aprType, memNo, memName, deptCode, deptName, docuDate,
				docuType);
		// TODO Auto-generated constructor stub
	}

	public AprFormOvt(char ovtType, Date ovtDate, int startHour, int startMinute, int endHour, int endMinute,
			int totalHour, String reasons) {
		super();
		this.ovtType = ovtType;
		this.ovtDate = ovtDate;
		this.startHour = startHour;
		this.startMinute = startMinute;
		this.endHour = endHour;
		this.endMinute = endMinute;
		this.totalHour = totalHour;
		this.reasons = reasons;
	}
	public char getOvtType() {
		return ovtType;
	}
	public Date getOvtDate() {
		return ovtDate;
	}
	public int getStartHour() {
		return startHour;
	}
	public int getStartMinute() {
		return startMinute;
	}
	public int getEndHour() {
		return endHour;
	}
	public int getEndMinute() {
		return endMinute;
	}
	public int getTotalHour() {
		return totalHour;
	}
	public String getReasons() {
		return reasons;
	}
	public void setOvtType(char ovtType) {
		this.ovtType = ovtType;
	}
	public void setOvtDate(Date ovtDate) {
		this.ovtDate = ovtDate;
	}
	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}
	public void setStartMinute(int startMinute) {
		this.startMinute = startMinute;
	}
	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}
	public void setEndMinute(int endMinute) {
		this.endMinute = endMinute;
	}
	public void setTotalHour(int totalHour) {
		this.totalHour = totalHour;
	}
	public void setReasons(String reasons) {
		this.reasons = reasons;
	}
	
}
