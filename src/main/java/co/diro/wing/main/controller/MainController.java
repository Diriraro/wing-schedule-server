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
import co.diro.wing.main.vo.ScheduleVo;

@RestController
public class MainController {
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JwtService jwtservice;
	@Autowired
	private MainService mainService;
	
	
	/**
	 * 스케줄 목록
	 * @return
	 */
	@GetMapping("/wingService/scheduleList")
	public Object getScheduleList(HttpServletRequest request, @RequestParam HashMap<String, String> params) {
		return mainService.getScheduleList(params);
	}
	
	/**
	 * 스케줄 상세
	 * @return
	 */
	@GetMapping("/wingService/schedule")
	public Object getSchedule(HttpServletRequest request, ScheduleVo scheduleVo) {
		return mainService.getSchedule(scheduleVo);
	}
	
	/**
	 * 스케줄 등록
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/wingService/schedule")
	public Object createSchedule(HttpServletRequest request, ScheduleVo scheduleVo) throws Exception {
		String userId = jwtservice.getUserId(request);
		scheduleVo.setScWriter(userId);
		return mainService.createSchedule(scheduleVo);
	}
	
	/**
	 * 스케줄 수정
	 * @return
	 * @throws Exception 
	 */
	@PutMapping("/wingService/schedule")
	public Object putSchedule(HttpServletRequest request, ScheduleVo scheduleVo) throws Exception {
		String userId = jwtservice.getUserId(request);
		scheduleVo.setScWriter(userId);
		return mainService.putSchedule(scheduleVo);
	}
	
	/**
	 * 스케줄 삭제
	 * @return
	 * @throws Exception 
	 */
	@DeleteMapping("/wingService/schedule")
	public Object deleteSchedule(HttpServletRequest request, ScheduleVo scheduleVo) throws Exception {
		String userId = jwtservice.getUserId(request);
		scheduleVo.setScWriter(userId);
		return mainService.deleteSchedule(scheduleVo);
	}
	
	/**
	 * 스케줄 참가 등록
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/wingService/scheduleJoin")
	public Object joinSchedule(HttpServletRequest request, ScheduleVo scheduleVo) throws Exception {
		String userId = jwtservice.getUserId(request);
		scheduleVo.setUserIdPk(userId);
		return mainService.joinSchedule(scheduleVo);
	}
	
	/**
	 * 스케줄 참가 수정
	 * @return
	 * @throws Exception 
	 */
	@PutMapping("/wingService/scheduleJoin")
	public Object putJoinSchedule(HttpServletRequest request, ScheduleVo scheduleVo) throws Exception {
		String userId = jwtservice.getUserId(request);
		scheduleVo.setUserIdPk(userId);
		return mainService.putJoinSchedule(scheduleVo);
	}
	
	/**
	 * 스케줄 참가 삭제
	 * @return
	 * @throws Exception 
	 */
	@DeleteMapping("/wingService/scheduleJoin")
	public Object deleteJoinSchedule(HttpServletRequest request, ScheduleVo scheduleVo) throws Exception {
		String userId = jwtservice.getUserId(request);
		scheduleVo.setUserIdPk(userId);
		return mainService.deleteJoinSchedule(scheduleVo);
	}
	
}
