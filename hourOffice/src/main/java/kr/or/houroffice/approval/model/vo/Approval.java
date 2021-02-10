package kr.or.houroffice.approval.model.vo;

import java.sql.Timestamp;

public class Approval {
	private int docuNo; //문서 번호 [날짜+연번5자리]
	private int memNo; // 사번
	private String deptCode; //부서코드
	private Timestamp docuDate; //문서작성일
	private char docuType; //문서양식 H:연차신청서/O:연장근무/L:지각불참사유/C:법인카드신청
	private String title; //제목
	private char aprType; //결재상태 W:대기/I:진행/R:반려/C:완료
	private char urgencyYN; //긴급여부 Y:긴급/N
	private char lockYN; //비공개여부 Y:비공개/N
	private char delYN; //삭제 여부 Y:삭제/N
	private Timestamp finalDate; //최종결재승인일 - 결재선 중 마지막 날짜
	
	//다른사람이 확인할 수 있게
	private String memName;
	
	public Approval() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Approval(int docuNo, int memNo, String deptCode, Timestamp docuDate, char docuType, String title,
			char aprType, char urgencyYN, char lockYN, char delYN, Timestamp finalDate, String memName) {
		super();
		this.docuNo = docuNo;
		this.memNo = memNo;
		this.deptCode = deptCode;
		this.docuDate = docuDate;
		this.docuType = docuType;
		this.title = title;
		this.aprType = aprType;
		this.urgencyYN = urgencyYN;
		this.lockYN = lockYN;
		this.delYN = delYN;
		this.finalDate = finalDate;
		this.memName = memName;
	}

	public int getDocuNo() {
		return docuNo;
	}

	public void setDocuNo(int docuNo) {
		this.docuNo = docuNo;
	}

	public int getMemNo() {
		return memNo;
	}

	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public Timestamp getDocuDate() {
		return docuDate;
	}

	public void setDocuDate(Timestamp docuDate) {
		this.docuDate = docuDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public char getAprType() {
		return aprType;
	}

	public void setAprType(char aprType) {
		this.aprType = aprType;
	}

	public char getUrgencyYN() {
		return urgencyYN;
	}

	public void setUrgencyYN(char urgencyYN) {
		this.urgencyYN = urgencyYN;
	}

	public char getLockYN() {
		return lockYN;
	}

	public void setLockYN(char lockYN) {
		this.lockYN = lockYN;
	}

	public char getDelYN() {
		return delYN;
	}

	public void setDelYN(char delYN) {
		this.delYN = delYN;
	}

	public Timestamp getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(Timestamp finalDate) {
		this.finalDate = finalDate;
	}

	public char getDocuType() {
		return docuType;
	}

	public String getMemName() {
		return memName;
	}

	public void setDocuType(char docuType) {
		this.docuType = docuType;
	}

	public void setMemName(String memName) {
		this.memName = memName;
	}
}
