package kr.or.houroffice.approval.model.vo;

import java.sql.Timestamp;

public class OvertimeForm {
	private int formNo; //양식번호
	private int docuNo; //결재문서 번호
	private char ovtType; // P:연장/O:야간/H:휴일
	private Timestamp ovtDate; //근무일
	private int startHour; //시작시
	private int startMinute; //시작분
	private int endHour; //종료시
	private int endMinute; //종료분
	private double totalHour; //총근무시간
	private String reasons; //신청사유

	public OvertimeForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OvertimeForm(int formNo, int docuNo, char ovtType, Timestamp ovtDate, int startHour, int startMinute,
			int endHour, int endMinute, double totalHour, String reasons) {
		super();
		this.formNo = formNo;
		this.docuNo = docuNo;
		this.ovtType = ovtType;
		this.ovtDate = ovtDate;
		this.startHour = startHour;
		this.startMinute = startMinute;
		this.endHour = endHour;
		this.endMinute = endMinute;
		this.totalHour = totalHour;
		this.reasons = reasons;
	}

	public int getFormNo() {
		return formNo;
	}

	public void setFormNo(int formNo) {
		this.formNo = formNo;
	}

	public int getDocuNo() {
		return docuNo;
	}

	public void setDocuNo(int docuNo) {
		this.docuNo = docuNo;
	}

	public char getOvtType() {
		return ovtType;
	}

	public void setOvtType(char ovtType) {
		this.ovtType = ovtType;
	}

	public Timestamp getOvtDate() {
		return ovtDate;
	}

	public void setOvtDate(Timestamp ovtDate) {
		this.ovtDate = ovtDate;
	}

	public int getStartHour() {
		return startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public int getStartMinute() {
		return startMinute;
	}

	public void setStartMinute(int startMinute) {
		this.startMinute = startMinute;
	}

	public int getEndHour() {
		return endHour;
	}

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	public int getEndMinute() {
		return endMinute;
	}

	public void setEndMinute(int endMinute) {
		this.endMinute = endMinute;
	}

	public double getTotalHour() {
		return totalHour;
	}

	public void setTotalHour(double totalHour) {
		this.totalHour = totalHour;
	}

	public String getReasons() {
		return reasons;
	}

	public void setReasons(String reasons) {
		this.reasons = reasons;
	}

}
