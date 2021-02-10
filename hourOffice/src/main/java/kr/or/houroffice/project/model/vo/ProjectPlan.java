package kr.or.houroffice.project.model.vo;

import java.sql.Date;

public class ProjectPlan {
	private int planNo;
	private int proNo;
	private int memNo;
	private String subject;
	private String memo;
	private Date startDate;
	private Date endDate;
	private char delYN;
	public ProjectPlan(int planNo, int proNo, int memNo, String subject, String memo, Date startDate, Date endDate,
			char delYN) {
		super();
		this.planNo = planNo;
		this.proNo = proNo;
		this.memNo = memNo;
		this.subject = subject;
		this.memo = memo;
		this.startDate = startDate;
		this.endDate = endDate;
		this.delYN = delYN;
	}
	public ProjectPlan() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getPlanNo() {
		return planNo;
	}
	public void setPlanNo(int planNo) {
		this.planNo = planNo;
	}
	public int getProNo() {
		return proNo;
	}
	public void setProNo(int proNo) {
		this.proNo = proNo;
	}
	public int getMemNo() {
		return memNo;
	}
	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public char getDelYN() {
		return delYN;
	}
	public void setDelYN(char delYN) {
		this.delYN = delYN;
	}
	@Override
	public String toString() {
		return "ProjectPlan [planNo=" + planNo + ", proNo=" + proNo + ", memNo=" + memNo + ", subject=" + subject
				+ ", memo=" + memo + ", startDate=" + startDate + ", endDate=" + endDate + ", delYN=" + delYN + "]";
	}
	
}
