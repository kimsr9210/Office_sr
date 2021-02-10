package kr.or.houroffice.member.model.vo;

import java.sql.Timestamp;

public class Attendance {
	private int memNo;
	private Timestamp startDate;
	private Timestamp endDate;
	private String todayWork;
	
	public Attendance() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Attendance(int memNo, Timestamp startDate, Timestamp endDate, String todayWork) {
		super();
		this.memNo = memNo;
		this.startDate = startDate;
		this.endDate = endDate;
		this.todayWork = todayWork;
	}
	
	public int getMemNo() {
		return memNo;
	}
	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public String getTodayWork() {
		return todayWork;
	}
	public void setTodayWork(String todayWork) {
		this.todayWork = todayWork;
	}
}
