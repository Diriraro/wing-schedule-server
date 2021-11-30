package co.diro.wing.common.vo;

import java.util.List;
import java.util.Map;

public class CommonVo extends PageVo {
	private String schValue;
	private String schValue2;
	private String schValue3;
	private String schValue4;
	private String schValue5;
	private String schValue6;
	private String schValue7;
	private String schCondition;
	private String schCondition2;
	private String schCondition3;
	private String schCondition4;
	private String schCondition5;
	private String sortField;
	private String sortOrder;
	private String fromDate;
	private String toDate;	
	
	// login 사용자 정보 (권한 체크 / 필터링 위함)
	private String loginUserId;			// 사용자ID
	private long   loginAuthgroupOid;	// 권한그룹ID
	private String loginUserOrganCd;	// 기관코드
	private String loginUserRegionId;	// 지역ID (시도)
	
	private String schListJson;
	private List<Map<String, Object>> schList;
	
	private String schAuthCondition; // 특수권한 체크용

	/* 통합검색조건 */
	private String totalSch;
	
	public String getSchValue() {
		return schValue;
	}
	public void setSchValue(String schValue) {
		this.schValue = schValue;
	}
	public String getSchValue2() {
		return schValue2;
	}
	public void setSchValue2(String schValue2) {
		this.schValue2 = schValue2;
	}
	public String getSchValue3() {
		return schValue3;
	}
	public void setSchValue3(String schValue3) {
		this.schValue3 = schValue3;
	}
	public String getSchValue4() {
		return schValue4;
	}
	public void setSchValue4(String schValue4) {
		this.schValue4 = schValue4;
	}
	public String getSchValue5() {
		return schValue5;
	}
	public void setSchValue5(String schValue5) {
		this.schValue5 = schValue5;
	}
	public String getSchValue6() {
		return schValue6;
	}
	public void setSchValue6(String schValue6) {
		this.schValue6 = schValue6;
	}
	public String getSchValue7() {
		return schValue7;
	}
	public void setSchValue7(String schValue7) {
		this.schValue7 = schValue7;
	}
	public String getSchCondition2() {
		return schCondition2;
	}
	public void setSchCondition2(String schCondition2) {
		this.schCondition2 = schCondition2;
	}
	public String getSchCondition3() {
		return schCondition3;
	}
	public void setSchCondition3(String schCondition3) {
		this.schCondition3 = schCondition3;
	}
	public String getSchCondition4() {
		return schCondition4;
	}
	public void setSchCondition4(String schCondition4) {
		this.schCondition4 = schCondition4;
	}
	public String getSchCondition5() {
		return schCondition5;
	}
	public void setSchCondition5(String schCondition5) {
		this.schCondition5 = schCondition5;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getSchCondition() {
		return schCondition;
	}
	public void setSchCondition(String schCondition) {
		this.schCondition = schCondition;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getLoginUserId() {
		return loginUserId;
	}
	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}
	public long getLoginAuthgroupOid() {
		return loginAuthgroupOid;
	}
	public void setLoginAuthgroupOid(long loginAuthgroupOid) {
		this.loginAuthgroupOid = loginAuthgroupOid;
	}
	public String getLoginUserOrganCd() {
		return loginUserOrganCd;
	}
	public void setLoginUserOrganCd(String loginUserOrganCd) {
		this.loginUserOrganCd = loginUserOrganCd;
	}
	public String getLoginUserRegionId() {
		return loginUserRegionId;
	}
	public void setLoginUserRegionId(String loginUserRegionId) {
		this.loginUserRegionId = loginUserRegionId;
	}
	public String getTotalSch() {
		return totalSch;
	}
	public void setTotalSch(String totalSch) {
		this.totalSch = totalSch;
	}
	public String getSchListJson() {
		return schListJson;
	}
	public void setSchListJson(String schListJson) {
		this.schListJson = schListJson;
	}
	public List<Map<String, Object>> getSchList() {
		return schList;
	}
	public void setSchList(List<Map<String, Object>> schList) {
		this.schList = schList;
	}
	public String getSchAuthCondition() {
		return schAuthCondition;
	}
	public void setSchAuthCondition(String schAuthCondition) {
		this.schAuthCondition = schAuthCondition;
	}

}
