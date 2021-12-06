package co.diro.wing.user.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Logger;
import co.diro.wing.user.service.UserService;
import co.diro.wing.user.vo.UserVo;

@RestController
public class UserController {
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	/**
	 * 가입 전 길드원 확인 체크 
	 * @return
	 */
	@GetMapping("/wingService/checkWingUser")
	public Object checkWingUser(HttpServletRequest request, UserVo userVo, @RequestParam HashMap<String, String> params) {
		return userService.checkWingUser(userVo, params);
	}
	
	/**
	 * 회원가입 
	 * @return
	 */
	@PostMapping("/wingService/wingUser")
	public Object joinWingUser(UserVo userVo, HttpServletRequest request) {
		return userService.joinWingUser(userVo, request);
	}
	
	
	
}