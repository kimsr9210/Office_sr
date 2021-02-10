package kr.or.houroffice.timecard.model.vo;


public class Holiday {
	
	private int listNo; //내역 연번
	private int  memNo; //사번
	private String title; //이름
	private String start; //휴가시작일
	private String end; //휴가종료일
	
	private char holType; //휴가구분 N:연차/C:경조사/P:공가/S:병가
	private char afternoonOff; //반차여부 반차여부 A:전일/M:오전/P:오후
	
	private int holCount; //사용갯수

	public int getListNo() {
		return listNo;
	}

	public void setListNo(int listNo) {
		this.listNo = listNo;
	}

	public int getMemNo() {
		return memNo;
	}

	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public char getHolType() {
		return holType;
	}

	public void setHolType(char holType) {
		this.holType = holType;
	}

	public char getAfternoonOff() {
		return afternoonOff;
	}

	public void setAfternoonOff(char afternoonOff) {
		this.afternoonOff = afternoonOff;
	}

	public int getHolCount() {
		return holCount;
	}

	public void setHolCount(int holCount) {
		this.holCount = holCount;
	}

	public Holiday(int listNo, int memNo, String title, String start, String end, char holType, char afternoonOff,
			int holCount) {
		super();
		this.listNo = listNo;
		this.memNo = memNo;
		this.title = title;
		this.start = start;
		this.end = end;
		this.holType = holType;
		this.afternoonOff = afternoonOff;
		this.holCount = holCount;
	}

	public Holiday() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
