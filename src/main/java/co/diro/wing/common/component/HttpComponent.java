
package co.diro.wing.common.component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import co.diro.wing.common.exception.GlobalException;
import okhttp3.HttpUrl;
import okhttp3.HttpUrl.Builder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class HttpComponent extends CommonComponent{
	
	public final static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	
	@Autowired
	OkHttpClient client;
		
	public ResponseEntity<Object> get(String uri) throws Exception{
		return get(uri, null);
	}
	public ResponseEntity<Object> get(String uri, Map<String, String> params) throws Exception{
		ResponseEntity<Object> responseEntity = null;
		try {
			
			Builder urlBuilder = HttpUrl.parse(uri).newBuilder();
			if(params != null) {
				for(Map.Entry<String, String> param : params.entrySet()) {
					urlBuilder.addQueryParameter(param.getKey(), param.getValue());
				}
			}
			
			Request.Builder builder = new Request.Builder();
			
			Request request = 	builder
					.addHeader("Accept", "*/*")
//					.addHeader("Content-Type", "Application/json")
					.url(urlBuilder.build())
					.get()
					.build();
			Response response = client().newCall(request).execute();
			
			if ( !response.isSuccessful()) {
				Exception ee = null;
				String message = response.body().string();
				
				/*if (response.code() == HttpStatus.UNAUTHORIZED.value()) {
					ee = new HttpUnauthorizedExcpetion(message);
				} else if (response.code() == HttpStatus.NOT_FOUND.value()) {
					ee = new HttpNotFoundException(message);
				} else if (response.code() == HttpStatus.FORBIDDEN.value()) {
					ee = new HttpForbiddenException(message);
				} else if (response.code() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
					ee = new HttpInternalServerErrorException(message);
				} else if (response.code() == HttpStatus.BAD_REQUEST.value()) {
					ee = new HttpBadRequestExcpetion(message);
				}*/
				ee = new GlobalException(message);
				throw ee;
			}
			responseEntity = new ResponseEntity<Object>(response.body().string(), HttpStatus.valueOf(response.code()));
		} catch (Exception ex) {
			throw ex;
		}
		
		return responseEntity;
	}

	public ResponseEntity<Object> request(String uri, Map<String, String> params) throws Exception{
		ResponseEntity<Object> responseEntity = null;
		try {
			
			Builder urlBuilder = HttpUrl.parse(uri).newBuilder();
			if(params != null) {
				for(Map.Entry<String, String> param : params.entrySet()) {
					urlBuilder.addQueryParameter(param.getKey(), param.getValue());
				}
			}
			
			Request.Builder builder = new Request.Builder();
			
			Request request = 	builder
					.addHeader("Accept", "*/*")
					.url(urlBuilder.build())
					.get()
					.build();
			Response response = client().newCall(request).execute();
			responseEntity = new ResponseEntity<Object>(response.body().string(), HttpStatus.valueOf(response.code()));
		} catch (Exception ex) {
			throw ex;
		}
		return responseEntity;
	}
	
	public ResponseEntity<Object> method(String method, String uri, Map<String, Object> params) throws Exception{
		ResponseEntity<Object> responseEntity = null;
		
		try {
			String json = toJson(params);
			RequestBody body = RequestBody.create(JSON, json);

			client = new OkHttpClient.Builder()
			        .connectTimeout(360, TimeUnit.SECONDS)
			        .writeTimeout(360, TimeUnit.SECONDS)
			        .readTimeout(360, TimeUnit.SECONDS)
			        .build();

			Request.Builder builder = new Request.Builder();

//			setTokenInfo(builder);
			
			Request request = 	builder
					.url(uri)
				    .method(method, body)
				    .build();
			
			Response response = client.newCall(request).execute();
			
			if ( !response.isSuccessful()) {
				Exception ee = null;
				String message = response.body().string();
				//TODO!! 사용자 프론트엔드에서 가져온 부분입니다.
				
				/*if (response.code() == HttpStatus.UNAUTHORIZED.value() || response.code() == HttpStatus.BAD_REQUEST.value()) {
					ee = new HttpUnauthorizedExcpetion(message);
				} else if (response.code() == HttpStatus.NOT_FOUND.value()) {
					ee = new HttpNotFoundException(message);
				} else if (response.code() == HttpStatus.FORBIDDEN.value()) {
					ee = new HttpForbiddenException(message);
				} else if (response.code() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
					ee = new HttpInternalServerErrorException(message);
				// 2020-05-06 422  처리할 수 없는 개체. 사용자 정의 ERROR 추가.
				} else if (response.code() == HttpStatus.UNPROCESSABLE_ENTITY.value()) {
					ee = new BizInfoException(message);
				}*/
				ee = new GlobalException(message);
				throw ee;
			}
			
			responseEntity = new ResponseEntity<Object>(response.body().string(), HttpStatus.valueOf(response.code()));
		} catch (Exception ex) { 
			throw ex;
		}
		
		return responseEntity;
	}
	
	
	/**
	 * <pre>헤더 변경 가능한 get client </pre>
	 * @return
	 */
	public String get(String uri, Map<String, String> headers, Map<String, String> params) throws Exception{
		ResponseEntity<Object> responseEntity = null;
		try {
			
			Builder urlBuilder = HttpUrl.parse(uri).newBuilder();
			if(params != null) {
				for(Map.Entry<String, String> param : params.entrySet()) {
					urlBuilder.addQueryParameter(param.getKey(), param.getValue());
				}
			}
			
			Request.Builder builder = new Request.Builder();
			
			builder
			.addHeader("Accept", "*/*")
			.url(urlBuilder.build())
			.get();
			
			if(headers != null) {
				for(Map.Entry<String, String> header : headers.entrySet()) {
					builder.addHeader(header.getKey(), header.getValue());
				}
			}
			
			Request request = 	builder.build();
			Response response = client.newCall(request).execute();
			
			if ( !response.isSuccessful()) {
				Exception ee = null;
				String message = response.body().string();
				
				/*if (response.code() == HttpStatus.UNAUTHORIZED.value()) {
					ee = new HttpUnauthorizedExcpetion(message);
				} else if (response.code() == HttpStatus.NOT_FOUND.value()) {
					ee = new HttpNotFoundException(message);
				} else if (response.code() == HttpStatus.FORBIDDEN.value()) {
					ee = new HttpForbiddenException(message);
				} else if (response.code() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
					ee = new HttpInternalServerErrorException(message);
				} else if (response.code() == HttpStatus.BAD_REQUEST.value()) {
					ee = new HttpBadRequestExcpetion(message);
				}*/
				ee = new GlobalException(message);
				throw ee;
			}
			responseEntity = new ResponseEntity<Object>(response.body().string(), HttpStatus.valueOf(response.code()));
		} catch (Exception ex) {
			throw ex;
		}
		
		return (String) responseEntity.getBody();
	}	
	
}
