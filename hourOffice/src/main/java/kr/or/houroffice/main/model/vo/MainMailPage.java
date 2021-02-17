package kr.or.houroffice.main.model.vo;

import kr.or.houroffice.common.Page;

public class MainMailPage extends Page{
	private int memNo;

	public MainMailPage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MainMailPage(int currentPage, int recordCountPerPage, int naviCountPerPage, int startPage, int endPage,
			int postTotalCount, int pageTotalCount, int startNavi, int endNavi, String url) {
		super(currentPage, recordCountPerPage, naviCountPerPage, startPage, endPage, postTotalCount, pageTotalCount, startNavi,
				endNavi, url);
		// TODO Auto-generated constructor stub
	}

	public MainMailPage(int memNo) {
		super();
		this.memNo = memNo;
	}

	public int getMemNo() {
		return memNo;
	}

	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}
	
}
