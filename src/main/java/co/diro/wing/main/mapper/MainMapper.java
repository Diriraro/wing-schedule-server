package co.diro.wing.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import co.diro.wing.main.vo.MainVo;
import co.diro.wing.notice.vo.NoticeVo;

@Mapper
public interface MainMapper {

	public int selectScheduleListCount(MainVo mainVo);
	
	public List<MainVo> selectScheduleList(MainVo mainVo);
	
	public MainVo selectSchedule(MainVo mainVo);
	
	public int insertSchedule(MainVo mainVo);
	
	public int updateSchedule(MainVo mainVo);
	
	public int deleteSchedule(MainVo mainVo);
	
	public int insertJoinSchedule(MainVo mainVo);
	
	public int updateJoinSchedule(MainVo mainVo);
	
	public int deleteJoinSchedule(MainVo mainVo);
	
}
