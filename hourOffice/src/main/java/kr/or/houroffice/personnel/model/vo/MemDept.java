package kr.or.houroffice.personnel.model.vo;

import java.sql.Date;

public class MemDept {
	private int memNo; //사원번호
	private String memPosition; //직위
	private String memName; //이름
	private String memBirth; //생일
	private String memAddress; //주소
	private String memPhone; //폰
	private String memTell; //사무실번호
	private String memEmail; //이메일
	private String memProfile; //직위
	private String deptName; //부서이름
	private Date licDate; //자격증딴날
	private String licName; //자격증이름
	private String licOrigin; //시행처
	private Date carJoinDate; //입사일
	private Date carResignDate; //퇴사일
	private String carPlace; //근무처
	private String carPosition; //전회사 직위
	private String carContent; //업무내용
	private String milServiceType;	 //복무종류
	private char memDelYN; //탈퇴여부 
	public MemDept() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MemDept(int memNo, String memPosition, String memName, String memBirth, String memAddress, String memPhone,
			String memTell, String memEmail, String memProfile, String deptName, Date licDate, String licName,
			String licOrigin, Date carJoinDate, Date carResignDate, String carPlace, String carPosition,
			String carContent, String milServiceType, char memDelYN) {
		super();
		this.memNo = memNo;
		this.memPosition = memPosition;
		this.memName = memName;
		this.memBirth = memBirth;
		this.memAddress = memAddress;
		this.memPhone = memPhone;
		this.memTell = memTell;
		this.memEmail = memEmail;
		this.memProfile = memProfile;
		this.deptName = deptName;
		this.licDate = licDate;
		this.licName = licName;
		this.licOrigin = licOrigin;
		this.carJoinDate = carJoinDate;
		this.carResignDate = carResignDate;
		this.carPlace = carPlace;
		this.carPosition = carPosition;
		this.carContent = carContent;
		this.milServiceType = milServiceType;
		this.memDelYN = memDelYN;
	}
	public int getMemNo() {
		return memNo;
	}
	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}
	public String getMemPosition() {
		return memPosition;
	}
	public void setMemPosition(String memPosition) {
		this.memPosition = memPosition;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getMemBirth() {
		return memBirth;
	}
	public void setMemBirth(String memBirth) {
		this.memBirth = memBirth;
	}
	public String getMemAddress() {
		return memAddress;
	}
	public void setMemAddress(String memAddress) {
		this.memAddress = memAddress;
	}
	public String getMemPhone() {
		return memPhone;
	}
	public void setMemPhone(String memPhone) {
		this.memPhone = memPhone;
	}
	public String getMemTell() {
		return memTell;
	}
	public void setMemTell(String memTell) {
		this.memTell = memTell;
	}
	public String getMemEmail() {
		return memEmail;
	}
	public void setMemEmail(String memEmail) {
		this.memEmail = memEmail;
	}
	public String getMemProfile() {
		return memProfile;
	}
	public void setMemProfile(String memProfile) {
		this.memProfile = memProfile;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Date getLicDate() {
		return licDate;
	}
	public void setLicDate(Date licDate) {
		this.licDate = licDate;
	}
	public String getLicName() {
		return licName;
	}
	public void setLicName(String licName) {
		this.licName = licName;
	}
	public String getLicOrigin() {
		return licOrigin;
	}
	public void setLicOrigin(String licOrigin) {
		this.licOrigin = licOrigin;
	}
	public Date getCarJoinDate() {
		return carJoinDate;
	}
	public void setCarJoinDate(Date carJoinDate) {
		this.carJoinDate = carJoinDate;
	}
	public Date getCarResignDate() {
		return carResignDate;
	}
	public void setCarResignDate(Date carResignDate) {
		this.carResignDate = carResignDate;
	}
	public String getCarPlace() {
		return carPlace;
	}
	public void setCarPlace(String carPlace) {
		this.carPlace = carPlace;
	}
	public String getCarPosition() {
		return carPosition;
	}
	public void setCarPosition(String carPosition) {
		this.carPosition = carPosition;
	}
	public String getCarContent() {
		return carContent;
	}
	public void setCarContent(String carContent) {
		this.carContent = carContent;
	}
	public String getMilServiceType() {
		return milServiceType;
	}
	public void setMilServiceType(String milServiceType) {
		this.milServiceType = milServiceType;
	}
	public char getMemDelYN() {
		return memDelYN;
	}
	public void setMemDelYN(char memDelYN) {
		this.memDelYN = memDelYN;
	}
	
}