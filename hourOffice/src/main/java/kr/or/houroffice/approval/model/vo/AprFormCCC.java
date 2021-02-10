package kr.or.houroffice.approval.model.vo;

import java.sql.Date;

public class AprFormCCC extends AprForm{//form에서 정보 받아오기 & form에 정보 뿌려주기
	//양식정보
	private int cardType;
	private String applier;
	private Date returnDate;
	private Date applyDate;
	private String applyDept;
	private String usedPlace;
	private int amounts;
	private String applyPurpose;
	public AprFormCCC() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AprFormCCC(int docuNo, String title, char lockYN, char urgencyYN, int[] aprLine, int[] aprRef, char aprType,
			int memNo, String memName, String deptCode, String deptName, Date docuDate, char docuType) {
		super(docuNo, title, lockYN, urgencyYN, aprLine, aprRef, aprType, memNo, memName, deptCode, deptName, docuDate,
				docuType);
		// TODO Auto-generated constructor stub
	}

	public AprFormCCC(int cardType, String applier, Date returnDate, Date applyDate, String applyDept, String usedPlace,
			int amounts, String applyPurpose) {
		super();
		this.cardType = cardType;
		this.applier = applier;
		this.returnDate = returnDate;
		this.applyDate = applyDate;
		this.applyDept = applyDept;
		this.usedPlace = usedPlace;
		this.amounts = amounts;
		this.applyPurpose = applyPurpose;
	}

	public int getCardType() {
		return cardType;
	}
	public String getApplier() {
		return applier;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public String getApplyDept() {
		return applyDept;
	}
	public String getUsedPlace() {
		return usedPlace;
	}
	public int getAmounts() {
		return amounts;
	}
	public String getApplyPurpose() {
		return applyPurpose;
	}
	public void setCardType(int cardType) {
		this.cardType = cardType;
	}
	public void setApplier(String applier) {
		this.applier = applier;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public void setApplyDept(String applyDept) {
		this.applyDept = applyDept;
	}
	public void setUsedPlace(String usedPlace) {
		this.usedPlace = usedPlace;
	}
	public void setAmounts(int amounts) {
		this.amounts = amounts;
	}
	public void setApplyPurpose(String applyPurpose) {
		this.applyPurpose = applyPurpose;
	}

}
