package co.diro.wing.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import co.diro.wing.user.vo.UserTokenVo;
import co.diro.wing.user.vo.UserVo;

@Mapper
public interface NoticeMapper {
	
	public UserVo selectCheckWingUser(UserVo userVo);
	
	public int insertWingUser(UserVo userVo);
	
	public UserTokenVo loginWingUser(UserVo userVo);
	
	public int updateCheckWingUser(UserVo userVo);
	
	public int insertPreMember(UserVo userVo);
	
	public UserVo selectUserData(UserVo userVo);

	public UserVo checkUserData(UserVo userVo);
	
	public String selectUserPwForChange(UserVo userVo);
	
	public int updateUserData(UserVo userVo);
}
