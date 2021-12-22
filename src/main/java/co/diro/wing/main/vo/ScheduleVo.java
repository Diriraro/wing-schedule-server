package co.diro.wing.main.vo;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.type.Alias;
import co.diro.wing.common.vo.CommonVo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("scheduleVo")
public class ScheduleVo{

	private int lkOid;			// 컨텐츠 oid
	private String lkContName;		// 컨텐츠 이름
	private String lkContMaxUsr;	// 컨텐츠 max 유저
	
	private int scOid;			// 스케쥴 oid
	private String scTitle;			// 스케줄 이름
	private String scWriter;		// 스케줄 작성자 id
	private String writerNickname;	// 스케줄 작성자 닉네임
	private String scContent;		// 스케줄 내용
	private String scCreDt;			// 스케줄 생성 시간
	private String scStartDt;		// 스케줄 시작 날짜
	private String scStartTime;		// 스케줄 시작 시간
	private boolean scNoticeSch;	// 공식 스케줄 여부
	
	private int joOid;			// 참가자 oid
	private String userIdPk;		// 참가자 id
	private String joNickname;		// 참가자 닉네임
	private String joUsrCondition;	// 참가자 상태
	private String joUsrClass;		// 참가자 직업
	
	private List<ScheduleVo> scList;
	
	private String fromDate;
	private String toDate;
	
	private int totalListSize;
	
}