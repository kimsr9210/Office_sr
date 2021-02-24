package kr.or.houroffice.member.model.vo;

import java.sql.Date;
import java.sql.Timestamp;

public class Member {
	private int rnum;
	private int memNo;
	private String memPwd;
	private String memPosition;
	private String deptCode;
	private String memName;
	private Date memBirth;
	private char memGender;
	private String memAddress;
	private String memPhone;
	private String memTell;
	private String memEmail;
	private String memProfile;
	private int memHolidayCount;
	private Timestamp memJoinDate;
	private char memResignYN;
	private Timestamp memResignDate;
	private char memDelYN;
	private Timestamp memDelDate;
	private char memRightCode;
	private String deptName;
	private Timestamp memRightDate;
	
	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Member(int rnum, int memNo, String memPwd, String memPosition, String deptCode, String memName,
			Date memBirth, char memGender, String memAddress, String memPhone, String memTell, String memEmail,
			String memProfile, int memHolidayCount, Timestamp memJoinDate, char memResignYN, Timestamp memResignDate,
			char memDelYN, Timestamp memDelDate, char memRightCode, String deptName, Timestamp memRightDate) {
		super();
		this.rnum = rnum;
		this.memNo = memNo;
		this.memPwd = memPwd;
		this.memPosition = memPosition;
		this.deptCode = deptCode;
		this.memName = memName;
		this.memBirth = memBirth;
		this.memGender = memGender;
		this.memAddress = memAddress;
		this.memPhone = memPhone;
		this.memTell = memTell;
		this.memEmail = memEmail;
		this.memProfile = memProfile;
		this.memHolidayCount = memHolidayCount;
		this.memJoinDate = memJoinDate;
		this.memResignYN = memResignYN;
		this.memResignDate = memResignDate;
		this.memDelYN = memDelYN;
		this.memDelDate = memDelDate;
		this.memRightCode = memRightCode;
		this.deptName = deptName;
		this.memRightDate = memRightDate;
	}

	public int getRnum() {
		return rnum;
	}

	public void setRnum(int rnum) {
		this.rnum = rnum;
	}

	public int getMemNo() {
		return memNo;
	}

	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}

	public String getMemPwd() {
		return memPwd;
	}

	public void setMemPwd(String memPwd) {
		this.memPwd = memPwd;
	}

	public String getMemPosition() {
		return memPosition;
	}

	public void setMemPosition(String memPosition) {
		this.memPosition = memPosition;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getMemName() {
		return memName;
	}

	public void setMemName(String memName) {
		this.memName = memName;
	}

	public Date getMemBirth() {
		return memBirth;
	}

	public void setMemBirth(Date memBirth) {
		this.memBirth = memBirth;
	}

	public char getMemGender() {
		return memGender;
	}

	public void setMemGender(char memGender) {
		this.memGender = memGender;
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

	public int getMemHolidayCount() {
		return memHolidayCount;
	}

	public void setMemHolidayCount(int memHolidayCount) {
		this.memHolidayCount = memHolidayCount;
	}

	public Timestamp getMemJoinDate() {
		return memJoinDate;
	}

	public void setMemJoinDate(Timestamp memJoinDate) {
		this.memJoinDate = memJoinDate;
	}

	public char getMemResignYN() {
		return memResignYN;
	}

	public void setMemResignYN(char memResignYN) {
		this.memResignYN = memResignYN;
	}

	public Timestamp getMemResignDate() {
		return memResignDate;
	}

	public void setMemResignDate(Timestamp memResignDate) {
		this.memResignDate = memResignDate;
	}

	public char getMemDelYN() {
		return memDelYN;
	}

	public void setMemDelYN(char memDelYN) {
		this.memDelYN = memDelYN;
	}

	public Timestamp getMemDelDate() {
		return memDelDate;
	}

	public void setMemDelDate(Timestamp memDelDate) {
		this.memDelDate = memDelDate;
	}

	public char getMemRightCode() {
		return memRightCode;
	}

	public void setMemRightCode(char memRightCode) {
		this.memRightCode = memRightCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Timestamp getMemRightDate() {
		return memRightDate;
	}

	public void setMemRightDate(Timestamp memRightDate) {
		this.memRightDate = memRightDate;
	}
	
	
}
