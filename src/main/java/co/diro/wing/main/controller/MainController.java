package co.diro.wing.main.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Logger;
import co.diro.wing.common.service.JwtService;
import co.diro.wing.main.service.MainService;
import co.diro.wing.main.vo.MainVo;

@RestController
public class MainController {
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JwtService jwtservice;
	@Autowired
	private MainService mainService;
	
	@GetMapping("/wingService/scheduleList")
	public Object getScheduleList(HttpServletRequest request, @RequestParam HashMap<String, String> params) {
		return mainService.getScheduleList(params);
	}
	
	@GetMapping("/wingService/schedule")
	public Object getSchedule(HttpServletRequest request, MainVo mainVo) {
		return mainService.getSchedule(mainVo);
	}
	
	@PostMapping("/wingService/schedule")
	public Object createSchedule(HttpServletRequest request, MainVo mainVo) {
		return mainService.createSchedule(mainVo);
	}
	
	@PutMapping("/wingService/schedule")
	public Object putSchedule(HttpServletRequest request, MainVo mainVo) {
		return mainService.putSchedule(mainVo);
	}
	
	@DeleteMapping("/wingService/schedule")
	public Object deleteSchedule(HttpServletRequest request, MainVo mainVo) {
		return mainService.deleteSchedule(mainVo);
	}
	
	@PostMapping("/wingService/scheduleJoin")
	public Object joinSchedule(HttpServletRequest request, MainVo mainVo) {
		return mainService.joinSchedule(mainVo);
	}
	
	@PutMapping("/wingService/scheduleJoin")
	public Object putJoinSchedule(HttpServletRequest request, MainVo mainVo) {
		return mainService.putJoinSchedule(mainVo);
	}
	
	@DeleteMapping("/wingService/scheduleJoin")
	public Object deleteJoinSchedule(HttpServletRequest request, MainVo mainVo) {
		return mainService.deleteJoinSchedule(mainVo);
	}
	
}
