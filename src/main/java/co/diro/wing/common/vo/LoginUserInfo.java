package co.diro.wing.common.vo;

public class LoginUserInfo {

	private String userId;
	private String nickname;
	private String userNm;
	private String phone;
	private String email;
	private String regionId;   		//시도
	private String d2RegionId; 		//시군구
	private String organCd;       	//기관코드
	private String authgroupOid;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getD2RegionId() {
		return d2RegionId;
	}
	public void setD2RegionId(String d2RegionId) {
		this.d2RegionId = d2RegionId;
	}
	public String getOrganCd() {
		return organCd;
	}
	public void setOrganCd(String organCd) {
		this.organCd = organCd;
	}
	public String getAuthgroupOid() {
		return authgroupOid;
	}
	public void setAuthgroupOid(String authgroupOid) {
		this.authgroupOid = authgroupOid;
	}
	@Override
	public String toString() {
		return "LoginUserInfo [userId=" + userId + ", nickname=" + nickname + ", userNm=" + userNm + ", phone=" + phone
				+ ", email=" + email + ", regionId=" + regionId + ", d2RegionId=" + d2RegionId + ", organCd=" + organCd
				+ ", authgroupOid=" + authgroupOid + "]";
	}
}
