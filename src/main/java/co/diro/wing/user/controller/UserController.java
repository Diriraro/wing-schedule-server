package co.diro.wing.user.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Logger;
import co.diro.wing.common.service.JwtService;
import co.diro.wing.user.service.UserService;
import co.diro.wing.user.vo.UserVo;

@RestController
public class UserController {
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtservice;
	
	/**
	 * 가입 전 길드원 확인 체크 
	 * @return
	 */
	@GetMapping("/wingService/wingUserCheck")
	public Object checkWingUser(HttpServletRequest request, @RequestParam HashMap<String, String> params) {
		return userService.checkWingUser(params);
	}
	
	/**
	 * 회원가입 
	 * @return
	 */
	@PostMapping("/wingService/wingUserCreate")
	public Object joinWingUser(@RequestBody UserVo userVo, HttpServletRequest request) {
		return userService.joinWingUser(userVo, request);
	}
	
	/**
	 * 로그인 
	 * @return
	 */
	@PostMapping("/wingService/wingUserLogin")
	public Object loginWingUser(@RequestBody UserVo userVo, HttpServletRequest request, HttpServletResponse response) {
		return userService.loginWingUser(userVo, request, response);
	}
	
	/**
	 * 새 길드원 pre테이블에 추가 
	 * @return
	 */
	@PostMapping("/wingService/wingPreMemPush")
	public Object pushPreMember(@RequestBody UserVo userVo, HttpServletRequest request) {
		return userService.pushPreMember(userVo, request);
	}
	
	/**
	 * 회원정보
	 * @return
	 */
	@GetMapping("/wingService/wingGetUsrData")
	public Object getUserData(HttpServletRequest request) {
		return userService.getUserData(request);
	}
	
	/**
	 * 회원정보 진입 전 비밀번호 인증
	 * @return
	 */
	@PostMapping("/wingService/wingCheckUsrData")
	public Object checkUserData(UserVo userVo,HttpServletRequest request) {
		return userService.checkUserData(userVo, request);
	}
	
	/**
	 * 회원정보 변경
	 * @return
	 */
	@PutMapping("/wingService/wingChangeUsrData")
	public Object changeUserData(UserVo userVo,HttpServletRequest request) {
		return userService.changeUserData(userVo, request);
	}
	
}
