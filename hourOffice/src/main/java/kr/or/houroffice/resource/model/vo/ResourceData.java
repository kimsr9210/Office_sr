package kr.or.houroffice.resource.model.vo;

import java.sql.Timestamp;

public class ResourceData {
	private int fileNo;
	private int memNo;
	private String category;
	private String originalFileName;
	private String changedFileName;
	private String filePath;
	private long fileSize;
	private Timestamp uploadTime;
	private char delYN;
	public ResourceData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResourceData(int fileNo, int memNo, String category, String originalFileName, String changedFileName,
			String filePath, long fileSize, Timestamp uploadTime, char delYN) {
		super();
		this.fileNo = fileNo;
		this.memNo = memNo;
		this.category = category;
		this.originalFileName = originalFileName;
		this.changedFileName = changedFileName;
		this.filePath = filePath;
		this.fileSize = fileSize;
		this.uploadTime = uploadTime;
		this.delYN = delYN;
	}
	@Override
	public String toString() {
		return "ResourceData [fileNo=" + fileNo + ", memNo=" + memNo + ", category=" + category + ", originalFileName="
				+ originalFileName + ", changedFileName=" + changedFileName + ", filePath=" + filePath + ", fileSize="
				+ fileSize + ", uploadTime=" + uploadTime + ", delYN=" + delYN + "]";
	}
	public int getFileNo() {
		return fileNo;
	}
	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}
	public int getMemNo() {
		return memNo;
	}
	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getChangedFileName() {
		return changedFileName;
	}
	public void setChangedFileName(String changedFileName) {
		this.changedFileName = changedFileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public Timestamp getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}
	public char getDelYN() {
		return delYN;
	}
	public void setDelYN(char delYN) {
		this.delYN = delYN;
	}
	
	
}
