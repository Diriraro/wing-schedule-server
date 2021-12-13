package co.diro.wing.user.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;
import co.diro.wing.common.component.CommonComponent;
import co.diro.wing.common.exception.GlobalException;
import co.diro.wing.common.service.JwtService;
import co.diro.wing.common.util.StringUtil;
import co.diro.wing.user.mapper.UserMapper;
import co.diro.wing.user.vo.UserTokenVo;
import co.diro.wing.user.vo.UserVo;


@Service
public class UserService extends CommonComponent{
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private JwtService jwtService;
	
	@Transactional
	public Object checkWingUser(UserVo userVo, HashMap<String, String> params) {
		logger("[가입체크] 서비스 시작");
		Map<String, Object> resMap = new HashMap<>();
		
		if(userVo.getNickname() != null) {
			try {

				userVo = userMapper.selectCheckWingUser(userVo);
				if(!userVo.getPreJoinCk()) {
					resMap.put("isCheck", userVo.getPreJoinCk());
					resMap.put("message", "가입 가능합니다.");
				}else {
					resMap.put("isCheck", userVo.getPreJoinCk());
					resMap.put("message", "이미 가입이 되어있습니다.");
				}
				logger("[가입체크] 체크완료");
				
			} catch (Exception e) {
				
				logger("[가입체크] 실패/오류");
				throw new GlobalException(e);
				
			}
		}else {
			logger("[가입체크] 실패/오류 - 아이디 입력 없음");
			throw new GlobalException();
		}
		logger("[가입체크] 서비스 종료");
		return makeResponseEntity(resMap, HttpStatus.OK);
	}
	
	@Transactional
	public Object joinWingUser(UserVo userVo, HttpServletRequest request) {
		logger("[회원가입] 서비스 시작");
		Map<String, Object> resMap = new HashMap<>();
		
		if(userVo.getNickname() == "레아잠" || userVo.getNickname() == "네프루아" || userVo.getNickname() == "바드그녀는신이다" || userVo.getNickname() == "윙슈즈") userVo.setRole("admin");
		else userVo.setRole("user");
		
		try {
			logger("[회원가입] 회원 등록");
			userMapper.insertWingUser(userVo);
			logger("[회원가입] 가입 성공 - pre_user 가입 체크");
			userMapper.updateCheckWingUser(userVo);
		} catch (Exception e) {
			logger("[회원가입] 실패/오류");
			throw new GlobalException();
		}
		logger("[회원가입] 서비스 종료");
		return makeResponseEntity(resMap, HttpStatus.CREATED);
	}
	
	@Transactional
	public Object loginWingUser(UserVo userVo, HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		logger("[로그인] 서비스 시작");
		Map<String, Object> resMap = new HashMap<>();
		
		try {
			UserTokenVo loginUsers = new UserTokenVo();
			logger("[로그인] 회원 확인");
			loginUsers = userMapper.loginWingUser(userVo);
			if(loginUsers.getUserIdPk() != null || loginUsers.getNickname() != null ) {
				
				logger("[로그인] 토큰생성");
//				UserVo loginUsers = userVo;
				String token = jwtService.create("member", loginUsers, "user");
				headers.add("Authorization", token);
				
			}
		} catch (Exception e) {
			logger("[로그인] 실패/오류");
			throw new GlobalException(e);
		}
		logger("[로그인] 서비스종료");
		return makeResponseEntity2(resMap, headers, HttpStatus.OK);
	}
	
	public ResponseEntity<Object> makeResponseEntity2(Object obj, HttpHeaders headers, HttpStatus status){
		try {
			return new ResponseEntity<Object>(obj, headers, status); 
		} catch (Exception e) {
			throw new GlobalException(e);
		}
	}
	
	public ResponseEntity<Object> makeResponseEntity(Object obj, HttpStatus status){
		HttpHeaders headers = new HttpHeaders();
		return makeResponseEntity(obj, headers, status); 
	}
	
}