package co.diro.wing.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import co.diro.wing.main.vo.NoticeVo;

@Mapper
public interface NoticeMapper {
	
	public int selectNoticeListCount(NoticeVo noticeVo);
	
	public List<NoticeVo> selectNoticeList(NoticeVo noticeVo);
	
	public NoticeVo selectNotice(NoticeVo noticeVo);
	
	public int insertNotice(NoticeVo noticeVo);
	
	public int updateNotice(NoticeVo noticeVo);
	
	public int deleteNotice(NoticeVo noticeVo);
	
}
