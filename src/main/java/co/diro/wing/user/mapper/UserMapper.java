package co.diro.wing.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import co.diro.wing.user.vo.UserVo;

@Mapper
public interface UserMapper {
	
	public UserVo selectCheckWingUser(UserVo userVo);
	
	public int insertWingUser(UserVo userVo);
	
	public UserVo selectWingUser(UserVo userVo);
	
	public int updateCheckWingUser(UserVo userVo);
}
