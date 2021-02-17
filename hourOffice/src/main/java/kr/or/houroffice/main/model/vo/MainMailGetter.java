package kr.or.houroffice.main.model.vo;

import java.sql.Timestamp;

public class MainMailGetter {
	private int mailNo;
	private int sendMemNo;
	private String title;
	private Timestamp sendDate;
	private String sendDateFormat;
	private int recMemNo;
	private char readYN;
	
	public MainMailGetter() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MainMailGetter(int mailNo, int sendMemNo, String title, Timestamp sendDate, String sendDateFormat,
			int recMemNo, char readYN) {
		super();
		this.mailNo = mailNo;
		this.sendMemNo = sendMemNo;
		this.title = title;
		this.sendDate = sendDate;
		this.sendDateFormat = sendDateFormat;
		this.recMemNo = recMemNo;
		this.readYN = readYN;
	}

	public int getMailNo() {
		return mailNo;
	}

	public void setMailNo(int mailNo) {
		this.mailNo = mailNo;
	}

	public int getSendMemNo() {
		return sendMemNo;
	}

	public void setSendMemNo(int sendMemNo) {
		this.sendMemNo = sendMemNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getSendDate() {
		return sendDate;
	}

	public void setSendDate(Timestamp sendDate) {
		this.sendDate = sendDate;
	}

	public String getSendDateFormat() {
		return sendDateFormat;
	}

	public void setSendDateFormat(String sendDateFormat) {
		this.sendDateFormat = sendDateFormat;
	}

	public int getRecMemNo() {
		return recMemNo;
	}

	public void setRecMemNo(int recMemNo) {
		this.recMemNo = recMemNo;
	}

	public char getReadYN() {
		return readYN;
	}

	public void setReadYN(char readYN) {
		this.readYN = readYN;
	}
}
