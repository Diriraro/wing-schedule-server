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
import co.diro.wing.common.exception.GlobalException;
import co.diro.wing.common.service.JwtService;
import co.diro.wing.common.util.StringUtil;
import co.diro.wing.main.vo.NoticeVo;
import co.diro.wing.user.mapper.UserMapper;
import co.diro.wing.user.vo.UserTokenVo;
import co.diro.wing.user.vo.UserVo;


@Service
public class NoticeService extends CommonComponent{
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JwtService jwtService;
	
	@Transactional
	public Object getNoticeList(HashMap<String, String> params) {
		logger("[공지사항 목록] 서비스 시작");
		Map<String, Object> resMap = new HashMap<>();
		
			try {

				logger("[공지사항 목록] 체크완료");
				
			} catch (Exception e) {
				
				logger("[공지사항 목록] 실패/오류");
				throw new GlobalException(e);
		}
		logger("[공지사항 목록] 서비스 종료");
		return makeResponseEntity(resMap, HttpStatus.OK);
	}
	
	@Transactional
	public Object getNotice(NoticeVo noticeVo) {
		logger("[공지사항 상세] 서비스 시작");
		Map<String, Object> resMap = new HashMap<>();
		
		try {
			
			logger("[공지사항 상세] 체크완료");
			
		} catch (Exception e) {
			
			logger("[공지사항 상세] 실패/오류");
			throw new GlobalException(e);
		}
		logger("[공지사항 상세] 서비스 종료");
		return makeResponseEntity(resMap, HttpStatus.OK);
	}
	
	@Transactional
	public Object createNotice(NoticeVo noticeVo) {
		logger("[공지사항 등록] 서비스 시작");
		Map<String, Object> resMap = new HashMap<>();
		
		try {
			
			logger("[공지사항 등록] 체크완료");
			
		} catch (Exception e) {
			
			logger("[공지사항 등록] 실패/오류");
			throw new GlobalException(e);
		}
		logger("[공지사항 등록] 서비스 종료");
		return makeResponseEntity(resMap, HttpStatus.OK);
	}
	
	@Transactional
	public Object changeNotice(NoticeVo noticeVo) {
		logger("[공지사항 수정] 서비스 시작");
		Map<String, Object> resMap = new HashMap<>();
		
		try {
			
			logger("[공지사항 수정] 체크완료");
			
		} catch (Exception e) {
			
			logger("[공지사항 수정] 실패/오류");
			throw new GlobalException(e);
		}
		logger("[공지사항 수정] 서비스 종료");
		return makeResponseEntity(resMap, HttpStatus.OK);
	}
	
	@Transactional
	public Object deleteNotice(NoticeVo noticeVo) {
		logger("[공지사항 삭제] 서비스 시작");
		Map<String, Object> resMap = new HashMap<>();
		
		try {
			
			logger("[공지사항 삭제] 체크완료");
			
		} catch (Exception e) {
			
			logger("[공지사항 삭제] 실패/오류");
			throw new GlobalException(e);
		}
		logger("[공지사항 삭제] 서비스 종료");
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