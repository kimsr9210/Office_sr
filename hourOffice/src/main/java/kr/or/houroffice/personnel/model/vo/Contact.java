package kr.or.houroffice.personnel.model.vo;

import java.sql.Date;
//개인주소록 vo
public class Contact {
	private int memNo; //사원번호
	private int cntNo; //게시물번호
	private String name; //이름
	private String company; //회사
	private String appointment; //직위
	private String ph; //폰번호
	private String officeNumber; //사무실번호
	private String email; //이메일주소
	private Date enrollDate; //작성일
	private char endYN; //삭제여부
	public int getMemNo() {
		return memNo;
	}
	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}
	public int getCntNo() {
		return cntNo;
	}
	public void setCntNo(int cntNo) {
		this.cntNo = cntNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getAppointment() {
		return appointment;
	}
	public void setAppointment(String appointment) {
		this.appointment = appointment;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public String getOfficeNumber() {
		return officeNumber;
	}
	public void setOfficeNumber(String officeNumber) {
		this.officeNumber = officeNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getEnrollDate() {
		return enrollDate;
	}
	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}
	public char getEndYN() {
		return endYN;
	}
	public void setEndYN(char endYN) {
		this.endYN = endYN;
	}
	public Contact(int memNo, int cntNo, String name, String company, String appointment, String ph,
			String officeNumber, String email, Date enrollDate, char endYN) {
		super();
		this.memNo = memNo;
		this.cntNo = cntNo;
		this.name = name;
		this.company = company;
		this.appointment = appointment;
		this.ph = ph;
		this.officeNumber = officeNumber;
		this.email = email;
		this.enrollDate = enrollDate;
		this.endYN = endYN;
	}
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
