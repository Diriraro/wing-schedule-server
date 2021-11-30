package co.diro.wing.common.component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ch.qos.logback.classic.Logger;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

@Component
public class CommonComponent {
	private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	private Gson gson;	
	
	private Gson gson2;	
	
	public CommonComponent() {
		gson = new GsonBuilder().create();
		
		// null 값도 표현하는 gson
		gson2 = new GsonBuilder()
		        .setPrettyPrinting()
		        .serializeNulls()
		        .create(); 
	}
	
	public List<Map<String, Object>> toList(String json) {
		return gson.fromJson(json, List.class);
	}
	public Map<String, Object> toMap(String json) {
		return gson.fromJson(json, Map.class);
	}
	public <T> T toObject(String jsonStr, Class<T> classOfT) {
	    return gson.fromJson(jsonStr, classOfT);
	}    
	public String toJson(Object obj) {
		return gson.toJson(obj);
	}
	public String toJsonWithNullValue(Object obj) {
		return gson2.toJson(obj);
	}
	
	/**
	 * 
	 * makeResponseEntity()
	 * 
	 * REST API HTTP 결과 객체 생성 메소드
	 * 
	 * @param obj
	 * @return
	 */
	public ResponseEntity<Object> makeResponseEntity(Object obj){
		HttpHeaders headers = new HttpHeaders();
		return makeResponseEntity(obj, headers, HttpStatus.OK); 
	}
	public ResponseEntity<Object> makeResponseEntity(Object obj, HttpStatus status){
		HttpHeaders headers = new HttpHeaders();
		return makeResponseEntity(obj, headers, status); 
	}
	public ResponseEntity<Object> makeResponseEntity(Object obj, HttpHeaders headers){
		return makeResponseEntity(obj, headers, HttpStatus.OK); 
	}
	public ResponseEntity<Object> makeResponseEntity(Object obj, HttpHeaders headers, HttpStatus status){
		headers.add("Content-Type", "application/json; chatset=utf8");
		return new ResponseEntity<Object>(obj, headers, status); 
	}
	
	public void logger(String msg, Object obj) {
		logger.info(">>>>>>>>>>"+msg, obj);
	}
	public void logger(String msg) {
		logger.info(">>>>>>>>>>"+msg);
	}
	
	@Bean
	public OkHttpClient client() {
		int maxConnections = 5;
		long keepDuration = 5;
		return new OkHttpClient().newBuilder()
				.connectionPool(new ConnectionPool(maxConnections, keepDuration, TimeUnit.MINUTES))
				.connectTimeout(180, TimeUnit.SECONDS)
				.writeTimeout(180, TimeUnit.SECONDS)
				.readTimeout(180, TimeUnit.SECONDS).build();
	}
}
