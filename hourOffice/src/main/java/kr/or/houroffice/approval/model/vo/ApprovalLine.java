package kr.or.houroffice.approval.model.vo;

import java.sql.Timestamp;

public class ApprovalLine {
	private int lineNo; // 결재선 번호
	private int docuNo; // 결재문서번호
	private int memNo; // 사번
	private char aprType; // 결재승인 A:승인/R:반려/W:대기
	private Timestamp aprDate; // 결재승인일
	private String aprComment; // 관련 의견
	
	//문서 조회시 필요한 정보
	private String memName;
	private String memPosition;
	private String deptName; 
	
	//결재상태 변경시 필요 정보
	private int lineCount; //결재선 갯수
	private int refusalCount; //반려 갯수
	
	public ApprovalLine() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ApprovalLine(int lineNo, int docuNo, int memNo, char aprType, Timestamp aprDate, String aprComment,
			String memName, String memPosition, String deptName, int lineCount, int refusalCount) {
		super();
		this.lineNo = lineNo;
		this.docuNo = docuNo;
		this.memNo = memNo;
		this.aprType = aprType;
		this.aprDate = aprDate;
		this.aprComment = aprComment;
		this.memName = memName;
		this.memPosition = memPosition;
		this.deptName = deptName;
		this.lineCount = lineCount;
		this.refusalCount = refusalCount;
	}

	public int getLineNo() {
		return lineNo;
	}

	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
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

	public char getAprType() {
		return aprType;
	}

	public void setAprType(char aprType) {
		this.aprType = aprType;
	}

	public Timestamp getAprDate() {
		return aprDate;
	}

	public void setAprDate(Timestamp aprDate) {
		this.aprDate = aprDate;
	}

	public String getAprComment() {
		return aprComment;
	}

	public void setAprComment(String aprComment) {
		this.aprComment = aprComment;
	}

	public String getMemName() {
		return memName;
	}

	public String getMemPosition() {
		return memPosition;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setMemName(String memName) {
		this.memName = memName;
	}

	public void setMemPosition(String memPosition) {
		this.memPosition = memPosition;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public int getLineCount() {
		return lineCount;
	}

	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}

	public int getRefusalCount() {
		return refusalCount;
	}

	public void setRefusalCount(int refusalCount) {
		this.refusalCount = refusalCount;
	}


}
