package kr.or.houroffice.common;

import java.util.ArrayList;

public class PageList<T> {
	private ArrayList<T> list;
	private String pageNavi;
	public PageList() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PageList(ArrayList<T> list, String pageNavi) {
		super();
		this.list = list;
		this.pageNavi = pageNavi;
	}
	public ArrayList<T> getList() {
		return list;
	}
	public String getPageNavi() {
		return pageNavi;
	}
	public void setList(ArrayList<T> list) {
		this.list = list;
	}
	public void setPageNavi(String pageNavi) {
		this.pageNavi = pageNavi;
	}
	
}
