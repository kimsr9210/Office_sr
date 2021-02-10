package kr.or.houroffice.personnel.model.vo;

import java.util.ArrayList;

public class PageData {
	
	// 페이징 처리된 데이터를저장하는 vo객체
	// getter setter 메소드 , 메개변수있는생성자 , 없는 생성자(default생성자) 만들기
	
	private ArrayList<Contact> list;
	private String pageNavi;
	public ArrayList<Contact> getList() {
		return list;
	}
	public void setList(ArrayList<Contact> list) {
		this.list = list;
	}
	public String getPageNavi() {
		return pageNavi;
	}
	public void setPageNavi(String pageNavi) {
		this.pageNavi = pageNavi;
	}
	public PageData(ArrayList<Contact> list, String pageNavi) {
		super();
		this.list = list;
		this.pageNavi = pageNavi;
	}
	public PageData() {
		super();
		// TODO Auto-generated constructor stub
	}

}
