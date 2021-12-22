package co.diro.wing.main.service;

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
import co.diro.wing.common.exception.AuthException;
import co.diro.wing.common.exception.GlobalException;
import co.diro.wing.common.service.JwtService;
import co.diro.wing.common.util.StringUtil;
import co.diro.wing.main.mapper.MainMapper;
import co.diro.wing.main.vo.ScheduleVo;
import co.diro.wing.notice.mapper.NoticeMapper;
import co.diro.wing.notice.vo.NoticeVo;
import co.diro.wing.user.mapper.UserMapper;
import co.diro.wing.user.vo.UserTokenVo;
import co.diro.wing.user.vo.UserVo;


@Service
public class MainService extends CommonComponent{
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private MainMapper mainMapper;
	
	@Transactional
	public Object getScheduleList(HashMap<String, String> params) {
		logger("[스케줄 목록] 서비스 시작");
		Map<String, Object> resMap = new HashMap<>();
		ScheduleVo scheduleVo = new ScheduleVo();
		try {

//			logger("[스케줄 목록] 목록 카운트 조회");
//			int totalListSize = mainMapper.selectScheduleListCount(scheduleVo);
//			scheduleVo.setTotalListSize(totalListSize);
			logger("[스케줄 목록] 목록 조회");
			List<ScheduleVo> list = mainMapper.selectScheduleList(scheduleVo);
			
			resMap.put("list", list);
//			resMap.put("page", scheduleVo);
		} catch (Exception e) {
			
			logger("[스케줄 목록] 실패/오류");
			throw new GlobalException(e);
		}
		logger("[스케줄 목록] 서비스 종료");
		return makeResponseEntity(resMap, HttpStatus.OK);
	}
	
	@Transactional
	public Object getSchedule(ScheduleVo scheduleVo) {
		logger("[스케줄 상세] 서비스 시작");
		Map<String, Object> resMap = new HashMap<>();
		try {
			
			logger("[스케줄 상세] 상세 조회");
			scheduleVo = mainMapper.selectSchedule(scheduleVo);
			
			List<ScheduleVo> joinList = mainMapper.selectJoinScheduleList(scheduleVo); 
			
			resMap.put("schedule", scheduleVo);
			resMap.put("join", joinList);
		} catch (Exception e) {
			
			logger("[스케줄 상세] 실패/오류");
			throw new GlobalException(e);
		}
		logger("[스케줄 상세] 서비스 종료");
		return makeResponseEntity(resMap, HttpStatus.OK);
	}
	
	@Transactional
	public Object createSchedule(ScheduleVo scheduleVo) {
		logger("[스케줄 등록] 서비스 시작");
		Map<String, Object> resMap = new HashMap<>();
		try {
			
			logger("[스케줄 등록] 아이디 조회 및 등록");
			if(!scheduleVo.getScWriter().equals("") || scheduleVo.getScWriter() != null) {
				mainMapper.insertSchedule(scheduleVo);
			}else {
				logger("[공지사항 수정] 토큰오류");
				throw new AuthException();
			}
		} catch (Exception e) {
			
			logger("[스케줄 등록] 실패/오류");
			throw new GlobalException(e);
		}
		logger("[스케줄 등록] 서비스 종료");
		return makeResponseEntity(resMap, HttpStatus.OK);
	}
	
	@Transactional
	public Object putSchedule(ScheduleVo scheduleVo) {
		logger("[스케줄 수정] 서비스 시작");
		Map<String, Object> resMap = new HashMap<>();
		try {
			
			logger("[스케줄 수정] 아이디 조회 및 수정");
			if(!scheduleVo.getScWriter().equals("") || scheduleVo.getScWriter() != null) {
				mainMapper.updateSchedule(scheduleVo);
			}else {
				logger("[공지사항 수정] 토큰오류");
				throw new AuthException();
			}
		} catch (Exception e) {
			
			logger("[스케줄 수정] 실패/오류");
			throw new GlobalException(e);
		}
		logger("[스케줄 수정] 서비스 종료");
		return makeResponseEntity(resMap, HttpStatus.OK);
	}
	
	@Transactional
	public Object deleteSchedule(ScheduleVo scheduleVo) {
		logger("[스케줄 삭제] 서비스 시작");
		Map<String, Object> resMap = new HashMap<>();
		try {
			
			logger("[스케줄 삭제] 아이디 삭제 및 삭제");
			if(!scheduleVo.getScWriter().equals("") || scheduleVo.getScWriter() != null) {
				mainMapper.deleteSchedule(scheduleVo);
			}else {
				logger("[공지사항 수정] 토큰오류");
				throw new AuthException();
			}
		} catch (Exception e) {
			
			logger("[스케줄 삭제] 실패/오류");
			throw new GlobalException(e);
		}
		logger("[스케줄 삭제] 서비스 종료");
		return makeResponseEntity(resMap, HttpStatus.OK);
	}
	
	@Transactional
	public Object joinSchedule(ScheduleVo scheduleVo) {
		logger("[스케줄 참가 등록] 서비스 시작");
		Map<String, Object> resMap = new HashMap<>();
		try {
			
			logger("[스케줄 참가 등록] 아이디 조회 및 참가 등록");
			if(!scheduleVo.getUserIdPk().equals("") || scheduleVo.getUserIdPk() != null) {
				mainMapper.insertJoinSchedule(scheduleVo);
			}else {
				logger("[공지사항 수정] 토큰오류");
				throw new AuthException();
			}
		} catch (Exception e) {
			
			logger("[스케줄 참가 등록] 실패/오류");
			throw new GlobalException(e);
		}
		logger("[스케줄 참가 등록] 서비스 종료");
		return makeResponseEntity(resMap, HttpStatus.OK);
	}
	
	@Transactional
	public Object putJoinSchedule(ScheduleVo scheduleVo) {
		logger("[스케줄 참가 수정] 서비스 시작");
		Map<String, Object> resMap = new HashMap<>();
		try {
			
			logger("[스케줄 참가 수정] 아이디 조회 및 참가 수정");
			if(!scheduleVo.getUserIdPk().equals("") || scheduleVo.getUserIdPk() != null) {
				mainMapper.updateJoinSchedule(scheduleVo);
			}else {
				logger("[공지사항 수정] 토큰오류");
				throw new AuthException();
			}
		} catch (Exception e) {
			
			logger("[스케줄 참가 수정] 실패/오류");
			throw new GlobalException(e);
		}
		logger("[스케줄 참가 수정] 서비스 종료");
		return makeResponseEntity(resMap, HttpStatus.OK);
	}
	
	@Transactional
	public Object deleteJoinSchedule(ScheduleVo scheduleVo) {
		logger("[스케줄 참가 삭제] 서비스 시작");
		Map<String, Object> resMap = new HashMap<>();
		try {
			
			logger("[스케줄 참가 삭제] 아이디 조회 및 참가 삭제");
			if(!scheduleVo.getUserIdPk().equals("") || scheduleVo.getUserIdPk() != null) {
				mainMapper.deleteJoinSchedule(scheduleVo);
			}else {
				logger("[공지사항 수정] 토큰오류");
				throw new AuthException();
			}
		} catch (Exception e) {
			
			logger("[스케줄 참가 삭제] 실패/오류");
			throw new GlobalException(e);
		}
		logger("[스케줄 참가 삭제] 서비스 종료");
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