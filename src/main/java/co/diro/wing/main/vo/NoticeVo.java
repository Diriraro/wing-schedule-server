package co.diro.wing.main.vo;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.type.Alias;
import co.diro.wing.common.vo.CommonVo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("noticeVo")
public class NoticeVo extends CommonVo{

	private String noticeOid;
	private String noticeTitle;
	private String noticeContent;
	private String noticeId;
	private Timestamp noticeCreDt;
	private String noticeCreUsrId;
	private List<NoticeVo> noticeList;
	
}