package co.diro.wing.user.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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
	public Object checkWingUser(HashMap<String, String> params) {
		logger("[가입체크] 서비스 시작");
		Map<String, Object> resMap = new HashMap<>();
		UserVo userVo = new UserVo();
		userVo.setNickname(params.get("nickname"));
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
	public Object loginWingUser(UserVo userVo, HttpServletRequest request, HttpServletResponse response) {
//		HttpHeaders headers = new HttpHeaders();
		logger("[로그인] 서비스 시작");
		Map<String, Object> resMap = new HashMap<>();
		ResponseCookie responseCookie = null;
		try {
			UserTokenVo loginUsers = new UserTokenVo();
			logger("[로그인] 회원 확인");
			loginUsers = userMapper.loginWingUser(userVo);
			if(loginUsers.getCharClass() != null || loginUsers.getNickname() != null ) {
				
				logger("[로그인] 토큰생성");
//				UserVo loginUsers = userVo;
				String token = jwtService.create("member", loginUsers, "user");
//				headers.add("Authorization", token);
				
				logger("[로그인] 쿠키생성 : "+token);
				responseCookie = ResponseCookie.from("auth", token) 
						.httpOnly(true)
						.secure(true)
						.path("/")
						.maxAge(1 * 10 * 60 * 60)
						.build();
				resMap.put("nickname", loginUsers.getNickname());
			}
		} catch (Exception e) {
			logger("[로그인] 실패/오류");
			throw new GlobalException(e);
		}
		logger("[로그인] 서비스종료");
		
		return ResponseEntity.ok()
		.header(HttpHeaders.SET_COOKIE, responseCookie.toString())
		.body(resMap);
//		return makeResponseEntity2(resMap, headers, HttpStatus.OK);
	}
	
	@Transactional
	public Object pushPreMember(UserVo userVo, HttpServletRequest request) {
		logger("[신규가입자 pre 등록] 서비스 시작");
		Map<String, Object> resMap = new HashMap<>();
		
		try {
			logger("[신규가입자 pre 등록] 등록 시작");
			userMapper.insertPreMember(userVo);
			
			logger("[신규가입자 pre 등록] 등록 완료");
		} catch (Exception e) {
			logger("[신규가입자 pre 등록] 실패/오류");
			throw new GlobalException(e);
		}
		
		logger("[신규가입자 pre 등록] 서비스종료");
		return makeResponseEntity(resMap, HttpStatus.CREATED);
	}
	
	@Transactional
	public Object getUserData(HttpServletRequest request) {
		Map<String, Object> resMap = new HashMap<>();
		logger("[유저정보 get] 서비스시작");
		try {
			logger("[유저정보 get] 토큰 복호화 시작");
			UserVo userVo = new UserVo();
			String token = request.getHeader("Authorization");
			if(token != null && jwtService.isUsable(token)) {
				Map<String, Object> tokenMap = jwtService.get("member");
				userVo.setUserIdPk(tokenMap.get("userIdPk")+"");
				userVo = userMapper.selectUserData(userVo);
				logger("[유저정보 get] 정보 가져오기 완료");
				resMap.put("user", userVo);
			}
		} catch (Exception e) {
			logger("[유저정보 get] 실패/오류");
			throw new GlobalException(e);
		}
		
		logger("[유저정보 get] 서비스종료");
		return makeResponseEntity(resMap, HttpStatus.OK);
	}
	
	@Transactional
	public Object checkUserData(UserVo userVo, HttpServletRequest request) {
		Map<String, Object> resMap = new HashMap<>();
		logger("[유저정보 check] 서비스시작");
		try {
			logger("[유저정보 check] 토큰 복호화 시작");
			String token = request.getHeader("Authorization");
			if(token != null && jwtService.isUsable(token)) {
				Map<String, Object> tokenMap = jwtService.get("member");
				userVo.setUserIdPk(tokenMap.get("userIdPk")+"");
				userVo = userMapper.checkUserData(userVo);
				logger("[유저정보 check] 회원 비밀번호 확인 완료");
			}
		} catch (Exception e) {
			logger("[유저정보 check] 실패/오류");
			throw new GlobalException(e);
		}
		
		logger("[유저정보 check] 서비스종료");
		return makeResponseEntity(resMap, HttpStatus.OK);
	}
	
	@Transactional
	public Object changeUserData(UserVo userVo, HttpServletRequest request) {
		Map<String, Object> resMap = new HashMap<>();
		logger("[유저정보 change] 서비스시작");
		try {
			logger("[유저정보 change] 토큰 복호화 시작");
			String token = request.getHeader("Authorization");
			if(token != null && jwtService.isUsable(token)) {
				Map<String, Object> tokenMap = jwtService.get("member");
				userVo.setUserIdPk(tokenMap.get("userIdPk")+"");
				
				if(userVo.getPassword() != null) {
					userVo.setPasswordOld(userMapper.selectUserPwForChange(userVo));
				}
				
				userMapper.updateUserData(userVo);
				logger("[유저정보 change] 회원정보 변경 완료");
			}
		} catch (Exception e) {
			logger("[유저정보 change] 실패/오류");
			throw new GlobalException(e);
		}
		
		logger("[유저정보 change] 서비스종료");
		return makeResponseEntity(resMap, HttpStatus.OK);
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