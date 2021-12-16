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
import co.diro.wing.main.service.NoticeService;
import co.diro.wing.main.vo.NoticeVo;

@RestController
public class NoticeController {
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JwtService jwtservice;
	@Autowired
	private NoticeService noticeService;
	
	/**
	 * 공지사항 목록
	 * @return
	 */
	@GetMapping("/wingService/getNoticeList")
	public Object getNoticeList(HttpServletRequest request, @RequestParam HashMap<String, String> params) {
		return noticeService.getNoticeList(params);
	}
	
	/**
	 * 공지사항 상세
	 * @return
	 */
	@GetMapping("/wingService/getNoticeList")
	public Object getNotice(NoticeVo noticeVo, HttpServletRequest request) {
		return noticeService.getNotice(noticeVo);
	}
	
	/**
	 * 공지사항 등록
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/wingService/getNoticeList")
	public Object createNotice(NoticeVo noticeVo, HttpServletRequest request) throws Exception {
		String userId = jwtservice.getUserId(request);
		noticeVo.setNoticeCreUsrId(userId);
		return noticeService.createNotice(noticeVo);
	}
	
	/**
	 * 공지사항 수정
	 * @return
	 * @throws Exception 
	 */
	@PutMapping("/wingService/getNoticeList")
	public Object changeNotice(NoticeVo noticeVo, HttpServletRequest request) throws Exception {
		String userId = jwtservice.getUserId(request);
		noticeVo.setNoticeCreUsrId(userId);
		return noticeService.changeNotice(noticeVo);
	}
	
	/**
	 * 공지사항 삭제
	 * @return
	 * @throws Exception 
	 */
	@DeleteMapping("/wingService/getNoticeList")
	public Object deleteNotice(NoticeVo noticeVo, HttpServletRequest request) throws Exception {
		String userId = jwtservice.getUserId(request);
		noticeVo.setNoticeCreUsrId(userId);
		return noticeService.deleteNotice(noticeVo);
	}
	
}
