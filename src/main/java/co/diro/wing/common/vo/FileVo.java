package co.diro.wing.common.vo;

public class FileVo{

	private String fileId;				//첨부파일ID
	private String bizCode;				//업무구분
	private String fileClassCd;			//파일구분CD
	private String bizId;				//업무ID
	private String fileOriginalName;	//원본파일명
	private String fileSavedName;		//저장파일명
	private String fileStoragePath;		//저장경로
	private int fileSize;				//파일크기
	private String fileCreUsrId;		//등록자ID
	private String fileCreDt;			//등록일시
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getBizCode() {
		return bizCode;
	}
	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}
	public String getFileClassCd() {
		return fileClassCd;
	}
	public void setFileClassCd(String fileClassCd) {
		this.fileClassCd = fileClassCd;
	}
	public String getBizId() {
		return bizId;
	}
	public void setBizId(String bizId) {
		this.bizId = bizId;
	}
	public String getFileOriginalName() {
		return fileOriginalName;
	}
	public void setFileOriginalName(String fileOriginalName) {
		this.fileOriginalName = fileOriginalName;
	}
	public String getFileSavedName() {
		return fileSavedName;
	}
	public void setFileSavedName(String fileSavedName) {
		this.fileSavedName = fileSavedName;
	}
	public String getFileStoragePath() {
		return fileStoragePath;
	}
	public void setFileStoragePath(String fileStoragePath) {
		this.fileStoragePath = fileStoragePath;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileCreUsrId() {
		return fileCreUsrId;
	}
	public void setFileCreUsrId(String fileCreUsrId) {
		this.fileCreUsrId = fileCreUsrId;
	}
	public String getFileCreDt() {
		return fileCreDt;
	}
	public void setFileCreDt(String fileCreDt) {
		this.fileCreDt = fileCreDt;
	}
	
	
}
