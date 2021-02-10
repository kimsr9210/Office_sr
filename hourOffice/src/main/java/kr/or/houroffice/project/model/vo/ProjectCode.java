package kr.or.houroffice.project.model.vo;

import java.sql.Timestamp;

public class ProjectCode {
	private int codeNo;
	private int proNo;
	private int memNo;
	private Timestamp codeDate;
	private String codeText;
	private String boardText;
	private char delYN;
	private String imgName;
	public ProjectCode() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProjectCode(int codeNo, int proNo, int memNo, Timestamp codeDate, String codeText, String boardText,
			char delYN, String imgName) {
		super();
		this.codeNo = codeNo;
		this.proNo = proNo;
		this.memNo = memNo;
		this.codeDate = codeDate;
		this.codeText = codeText;
		this.boardText = boardText;
		this.delYN = delYN;
		this.imgName = imgName;
	}
	public int getCodeNo() {
		return codeNo;
	}
	public void setCodeNo(int codeNo) {
		this.codeNo = codeNo;
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
	public Timestamp getCodeDate() {
		return codeDate;
	}
	public void setCodeDate(Timestamp codeDate) {
		this.codeDate = codeDate;
	}
	public String getCodeText() {
		return codeText;
	}
	public void setCodeText(String codeText) {
		this.codeText = codeText;
	}
	public String getBoardText() {
		return boardText;
	}
	public void setBoardText(String boardText) {
		this.boardText = boardText;
	}
	public char getDelYN() {
		return delYN;
	}
	public void setDelYN(char delYN) {
		this.delYN = delYN;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	@Override
	public String toString() {
		return "ProjectCode [codeNo=" + codeNo + ", proNo=" + proNo + ", memNo=" + memNo + ", codeDate=" + codeDate
				+ ", codeText=" + codeText + ", boardText=" + boardText + ", delYN=" + delYN + ", imgName=" + imgName
				+ "]";
	}
	
	
}
