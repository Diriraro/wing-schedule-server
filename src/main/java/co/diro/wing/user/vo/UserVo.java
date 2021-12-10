package co.diro.wing.user.vo;

import org.apache.ibatis.type.Alias;
import co.diro.wing.common.vo.CommonVo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("userVo")
public class UserVo{

	private String userIdPk;
	private String password;
	private String nickname;
	private String email;
	private String charClass;
	private String role;
	private String passwordOld;
	private Boolean preJoinCk;
	
}