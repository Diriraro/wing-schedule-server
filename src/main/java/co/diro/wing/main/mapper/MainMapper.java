package co.diro.wing.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import co.diro.wing.main.vo.ScheduleVo;

@Mapper
public interface MainMapper {

	public int selectScheduleListCount(ScheduleVo scheduleVo);
	
	public List<ScheduleVo> selectScheduleList(ScheduleVo scheduleVo);
	
	public ScheduleVo selectSchedule(ScheduleVo scheduleVo);

	public int selectJoinScheduleListCount(ScheduleVo scheduleVo);
	
	public List<ScheduleVo> selectJoinScheduleList(ScheduleVo scheduleVo);
	
	public int insertSchedule(ScheduleVo scheduleVo);
	
	public int updateSchedule(ScheduleVo scheduleVo);
	
	public int deleteSchedule(ScheduleVo scheduleVo);
	
	public int insertJoinSchedule(ScheduleVo scheduleVo);
	
	public int updateJoinSchedule(ScheduleVo scheduleVo);
	
	public int deleteJoinSchedule(ScheduleVo scheduleVo);
	
}
