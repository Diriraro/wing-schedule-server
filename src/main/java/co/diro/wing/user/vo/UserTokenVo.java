package co.diro.wing.user.vo;

import org.apache.ibatis.type.Alias;
import co.diro.wing.common.vo.CommonVo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("userTokenVo")
public class UserTokenVo{

	private String userIdPk;
	private String nickname;
	private String email;
	private String charClass;
	private String role;
	
}