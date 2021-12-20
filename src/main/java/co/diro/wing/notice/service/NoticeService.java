package co.diro.wing.notice.service;

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
import co.diro.wing.notice.mapper.NoticeMapper;
import co.diro.wing.notice.vo.NoticeVo;
import co.diro.wing.user.mapper.UserMapper;
import co.diro.wing.user.vo.UserTokenVo;
import co.diro.wing.user.vo.UserVo;


@Service
public class NoticeService extends CommonComponent{
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private NoticeMapper noticeMapper;
	
	@Transactional
	public Object getNoticeList(HashMap<String, String> params) {
		logger("[공지사항 목록] 서비스 시작");
		Map<String, Object> resMap = new HashMap<>();
		NoticeVo noticeVo = new NoticeVo();
			try {

				logger("[공지사항 목록] 목록 카운트 조회");
				int totalListSize = noticeMapper.selectNoticeListCount(noticeVo);
				noticeVo.setTotalListSize(totalListSize);
				logger("[공지사항 목록] 목록 조회");
				List<NoticeVo> list = noticeMapper.selectNoticeList(noticeVo);
				
				resMap.put("list", list);
				resMap.put("page", noticeVo);
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
			logger("[공지사항 상세] 조회");
			noticeVo = noticeMapper.selectNotice(noticeVo);
			
			resMap.put("notice", noticeVo);
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
			
			logger("[공지사항 등록] 아이디 조회 및 등록");
			if(!noticeVo.getNoticeCreUsrId().equals("") || noticeVo.getNoticeCreUsrId() != null) {
				noticeMapper.insertNotice(noticeVo);
			}else {
				logger("[공지사항 등록] 토큰오류");
				throw new AuthException();
			}
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
			
			logger("[공지사항 수정] 수정");
			if(!noticeVo.getNoticeCreUsrId().equals("") || noticeVo.getNoticeCreUsrId() != null) {
				noticeMapper.updateNotice(noticeVo);
			}else {
				logger("[공지사항 수정] 토큰오류");
				throw new AuthException();
			}
			
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
			logger("[공지사항 삭제] 삭제");
			if(!noticeVo.getNoticeCreUsrId().equals("") || noticeVo.getNoticeCreUsrId() != null) {
				noticeMapper.deleteNotice(noticeVo);
			}else {
				logger("[공지사항 삭제] 토큰오류");
				throw new AuthException();
			}
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