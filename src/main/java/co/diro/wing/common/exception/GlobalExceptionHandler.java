package co.diro.wing.common.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ch.qos.logback.classic.Logger;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(AuthException.class)
	public ResponseEntity<String> handleAuthException(HttpServletRequest request, AuthException ex) {
		return getResponseEntity(HttpStatus.UNAUTHORIZED, ex);
	}
	
	/**
	 * <pre>접근 제한시 403 관련</pre>
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<String> handleForbiddenException(HttpServletRequest request, ForbiddenException ex) {
		return getResponseEntity(HttpStatus.FORBIDDEN, ex);
	}
	
	/**
	 * <pre>내부 로직 처리 오류 발생 시 500 관련</pre>
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(GlobalException.class)
	public ResponseEntity<String> handleTokenException(HttpServletRequest request, GlobalException ex) {
		return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex);
	}
	
	/**
	 * <pre>내부 로직 처리 오류 발생 시 500 관련</pre>
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleTokenException(HttpServletRequest request, Exception ex) {
		return getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex);
	}
	
	/**
	 * <pre>리턴메시지 반환</pre>
	 * @param code
	 * @param message
	 * @return
	 */
	public String getResponseMessage(int type, String title, String detail) {
		return String.format("{\"type\":\"%d\",\"title\":\"%s\",\"detail\": \"%s\"}", type, title, detail);
	}
	
	/**
	 * <pre>ResponseEntity 객체 반환</pre>
	 * @param status
	 * @param e
	 * @return
	 */
	public ResponseEntity<String> getResponseEntity(HttpStatus status, Exception e){
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json;");
		int code = 0;
		String infoMessage = ""; // exception 메시지 는 보안 위배 문제 때문에 막음. 사용자 메시지만 사용. 실 서비스 시 메시지 막아야함
		
		if(status.getReasonPhrase().equals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())){
			code = 5000;
			infoMessage = e.getMessage();
		}else if(status.getReasonPhrase().equals(HttpStatus.UNAUTHORIZED.getReasonPhrase())){
			code = 4001;
			infoMessage = e.getMessage();
		}else if(status.getReasonPhrase().equals(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())){
			code = 4022;
			infoMessage = e.getMessage();
		}else{
			//TODO:
		}
		String body = getResponseMessage(code, status.getReasonPhrase(), infoMessage);
		return new ResponseEntity<>(body, headers, status);
	}
	
}
