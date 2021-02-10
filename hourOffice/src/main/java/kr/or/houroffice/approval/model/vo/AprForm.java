package kr.or.houroffice.approval.model.vo;

import java.sql.Date;

public class AprForm {//form에서 정보 받아오기 & form에 정보 뿌려주기
	//공통 정보
	protected int docuNo; //문서번호
	protected String title; //제목
	protected char lockYN; //비공개 여부 on
	protected char urgencyYN; //긴급여부 on
	protected int[] aprLine; //결재선 리스트
	protected int[] aprRef; //결재참조 리스트
	protected char aprType; //결재 상태
	
	//작성자 정보
	protected int memNo; //작성자 사번
	protected String memName; //작성자 이름
	protected String deptCode; //부서코드
	protected String deptName; //부서명
	protected Date docuDate; //작성일
	protected char docuType; //문서 타입
	
	public AprForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AprForm(int docuNo, String title, char lockYN, char urgencyYN, int[] aprLine, int[] aprRef,
			char aprType, int memNo, String memName, String deptCode, String deptName, Date docuDate, char docuType) {
		super();
		this.docuNo = docuNo;
		this.title = title;
		this.lockYN = lockYN;
		this.urgencyYN = urgencyYN;
		this.aprLine = aprLine;
		this.aprRef = aprRef;
		this.aprType = aprType;
		this.memNo = memNo;
		this.memName = memName;
		this.deptCode = deptCode;
		this.deptName = deptName;
		this.docuDate = docuDate;
		this.docuType = docuType;
	}

	public int getDocuNo() {
		return docuNo;
	}

	public String getTitle() {
		return title;
	}

	public char getLockYN() {
		return lockYN;
	}

	public char getUrgencyYN() {
		return urgencyYN;
	}

	public int[] getAprLine() {
		return aprLine;
	}

	public int[] getAprRef() {
		return aprRef;
	}

	public char getAprType() {
		return aprType;
	}

	public int getMemNo() {
		return memNo;
	}

	public String getMemName() {
		return memName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public Date getDocuDate() {
		return docuDate;
	}

	public char getDocuType() {
		return docuType;
	}

	public void setDocuNo(int docuNo) {
		this.docuNo = docuNo;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setLockYN(char lockYN) {
		this.lockYN = lockYN;
	}

	public void setUrgencyYN(char urgencyYN) {
		this.urgencyYN = urgencyYN;
	}

	public void setAprLine(int[] aprLine) {
		this.aprLine = aprLine;
	}

	public void setAprRef(int[] aprRef) {
		this.aprRef = aprRef;
	}

	public void setAprType(char aprType) {
		this.aprType = aprType;
	}

	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}

	public void setMemName(String memName) {
		this.memName = memName;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setDocuDate(Date docuDate) {
		this.docuDate = docuDate;
	}

	public void setDocuType(char docuType) {
		this.docuType = docuType;
	}

}
