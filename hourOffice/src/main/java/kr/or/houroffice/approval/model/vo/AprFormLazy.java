package kr.or.houroffice.approval.model.vo;

import java.sql.Date;

public class AprFormLazy extends AprForm{//form에서 정보 받아오기 & form에 정보 뿌려주기
	//양식정보
	private String dept;
	private String position;
	private String mName;
	private Date lazyDate;
	private String reasons;
	
	

	public AprFormLazy() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AprFormLazy(int docuNo, String title, char lockYN, char urgencyYN, int[] aprLine, int[] aprRef, char aprType,
			int memNo, String memName, String deptCode, String deptName, Date docuDate, char docuType) {
		super(docuNo, title, lockYN, urgencyYN, aprLine, aprRef, aprType, memNo, memName, deptCode, deptName, docuDate,
				docuType);
		// TODO Auto-generated constructor stub
	}

	public AprFormLazy(String dept, String position, String mName, Date lazyDate, String reasons) {
		super();
		this.dept = dept;
		this.position = position;
		this.mName = mName;
		this.lazyDate = lazyDate;
		this.reasons = reasons;
	}

	public String getDept() {
		return dept;
	}

	public String getPosition() {
		return position;
	}

	public String getmName() {
		return mName;
	}

	public Date getLazyDate() {
		return lazyDate;
	}

	public String getReasons() {
		return reasons;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public void setLazyDate(Date lazyDate) {
		this.lazyDate = lazyDate;
	}

	public void setReasons(String reasons) {
		this.reasons = reasons;
	}
}
