package kr.or.houroffice.mail.model.vo;

import java.util.ArrayList;

public class MailList {
	private int memNo; //요청한 사번
	private ArrayList<Integer> mailNoList;//메일번호
	private char listType; //DB에서 테이블 구분
	
	public MailList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MailList(int memNo, ArrayList<Integer> mailNoList, char listType) {
		super();
		this.memNo = memNo;
		this.mailNoList = mailNoList;
		this.listType = listType;
	}

	public int getMemNo() {
		return memNo;
	}
	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}
	public char getListType() {
		return listType;
	}
	public void setListType(char listType) {
		this.listType = listType;
	}
	public ArrayList<Integer> getMailNoList() {
		return mailNoList;
	}
	public void setMailNoList(ArrayList<Integer> mailNoList) {
		this.mailNoList = mailNoList;
	}
	
}
