package kr.or.houroffice.admin.model.vo;

import java.sql.Timestamp;

public class AdminBoard {
	private int rnum; //삭제 순서대로 정렬
	private int noNum; //게시물 번호
	private String title; //게시물 제목
	private String writer; //게시물 작성자
	private Timestamp writeDate; //게시글 작성일
	private int hits; //게시글 조회수
	private char delYN; //게시글 삭제 여부
	private Timestamp delDate; //게시글 삭제일
	
	public AdminBoard() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AdminBoard(int rnum, int noNum, String title, String writer, Timestamp writeDate, int hits, char delYN,
			Timestamp delDate) {
		super();
		this.rnum = rnum;
		this.noNum = noNum;
		this.title = title;
		this.writer = writer;
		this.writeDate = writeDate;
		this.hits = hits;
		this.delYN = delYN;
		this.delDate = delDate;
	}
	public int getRnum() {
		return rnum;
	}
	public void setRnum(int rnum) {
		this.rnum = rnum;
	}
	public int getNoNum() {
		return noNum;
	}
	public void setNoNum(int noNum) {
		this.noNum = noNum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Timestamp getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(Timestamp writeDate) {
		this.writeDate = writeDate;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public char getDelYN() {
		return delYN;
	}
	public void setDelYN(char delYN) {
		this.delYN = delYN;
	}
	public Timestamp getDelDate() {
		return delDate;
	}
	public void setDelDate(Timestamp delDate) {
		this.delDate = delDate;
	}
}//AdminBoard
