package kr.or.houroffice.board.model.vo;

import java.sql.Date;
import java.sql.Timestamp;

public class PartBoard {
	
	private int partNo;				// 게시물 고유번호
	private String partTitle;		// 게시물 제목
	private String deptCode;		// 부서 고유번호
	private Timestamp partDate;		// 게시물 작성일
	private int memNo;				// 작성자 사번
	private String partWriter;		// 작성자 이름
	private String partContent;		// 게시물 내용
	private int partHits;			// 게시물 조회수
	private char partDelYN;			// 게시물 삭제 YN
	private Timestamp partDelDate;	// 게시물 삭제일
	private String deptName;		// 부서 이름
	public PartBoard() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PartBoard(int partNo, String partTitle, String deptCode, Timestamp partDate, int memNo, String partWriter,
			String partContent, int partHits, char partDelYN, Timestamp partDelDate, String deptName) {
		super();
		this.partNo = partNo;
		this.partTitle = partTitle;
		this.deptCode = deptCode;
		this.partDate = partDate;
		this.memNo = memNo;
		this.partWriter = partWriter;
		this.partContent = partContent;
		this.partHits = partHits;
		this.partDelYN = partDelYN;
		this.partDelDate = partDelDate;
		this.deptName = deptName;
	}
	public int getPartNo() {
		return partNo;
	}
	public void setPartNo(int partNo) {
		this.partNo = partNo;
	}
	public String getPartTitle() {
		return partTitle;
	}
	public void setPartTitle(String partTitle) {
		this.partTitle = partTitle;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public Timestamp getPartDate() {
		return partDate;
	}
	public void setPartDate(Timestamp partDate) {
		this.partDate = partDate;
	}
	public int getMemNo() {
		return memNo;
	}
	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}
	public String getPartWriter() {
		return partWriter;
	}
	public void setPartWriter(String partWriter) {
		this.partWriter = partWriter;
	}
	public String getPartContent() {
		return partContent;
	}
	public void setPartContent(String partContent) {
		this.partContent = partContent;
	}
	public int getPartHits() {
		return partHits;
	}
	public void setPartHits(int partHits) {
		this.partHits = partHits;
	}
	public char getPartDelYN() {
		return partDelYN;
	}
	public void setPartDelYN(char partDelYN) {
		this.partDelYN = partDelYN;
	}
	public Timestamp getPartDelDate() {
		return partDelDate;
	}
	public void setPartDelDate(Timestamp partDelDate) {
		this.partDelDate = partDelDate;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
}
