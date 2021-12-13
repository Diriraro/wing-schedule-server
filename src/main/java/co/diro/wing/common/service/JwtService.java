package co.diro.wing.common.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;

import co.diro.wing.common.component.CommonComponent;
import co.diro.wing.common.exception.GlobalException;
import co.diro.wing.user.vo.UserTokenVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("jwtService")
public class JwtService{

	@Value("${jwt.secret}")
    private String SALT;
    
	@Autowired
	private CommonComponent commonComponent;
	
    public String create(String key, UserTokenVo data, String subject){
    	
    	String json = commonComponent.toJson(data);
    	try {
//    		System.out.println(json);
    		
    		
    		JwtBuilder builder = Jwts.builder()
    				.setSubject(subject)
    				.setHeaderParam("typ", "JWT")
    				.setHeaderParam("regDate", System.currentTimeMillis())
    				.setExpiration(new Date(System.currentTimeMillis() + 86400 * 1000 * 2))
    				.claim(key, json)
    				.signWith(SignatureAlgorithm.HS256, this.generateKey());
    		
//    		String jwt = Jwts.builder()
//    				.setSubject(subject)
//    				.setHeaderParam("typ", "JWT")
//    				.setHeaderParam("regDate", System.currentTimeMillis())
//    				.setExpiration(new Date(System.currentTimeMillis() + 86400 * 1000 * 2))
//    				.claim(key, data)
//    				.signWith(SignatureAlgorithm.HS256, this.generateKey())
//    				.compact();
    		return builder.compact();
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new GlobalException(e);
		}
    }   
 
    private byte[] generateKey(){
        byte[] key = null;
        try {
            key = SALT.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            if(log.isInfoEnabled()){
                e.printStackTrace();
            }else{
                log.error("Making JWT Key Error ::: {}", e.getMessage());
            }
        }
         
        return key;
    }

	public Map<String, Object> get(String key) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String jwt = request.getHeader("Authorization").split(" ")[1];
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser()
						 .setSigningKey(SALT.getBytes("UTF-8"))
						 .parseClaimsJws(jwt);
		} catch (Exception e) {
			if(log.isInfoEnabled()){
				e.printStackTrace();
			}else{
				log.error(e.getMessage());
			}
			log.error(e.getMessage());
			throw new GlobalException(e);
//			throw new UnauthorizedException();
		}
		@SuppressWarnings("unchecked")
        Map<String, Object> value = (LinkedHashMap<String, Object>)claims.getBody().get(key);
        System.out.println(value);
		return value;
	}

	public int getMemberId() {
		return (Integer) this.get("member").get("id");
	}

	public boolean isUsable(String jwt) {
		try{
			Jws<Claims> claims = Jwts.parser()
					  .setSigningKey(this.generateKey())
					  .parseClaimsJws(jwt);
			return true;
			
		}catch (Exception e) {
			
			if(log.isInfoEnabled()){
				e.printStackTrace();
			}else{
				log.error(e.getMessage());
			}
			throw new GlobalException();
//			throw new UnauthorizedException();

			/*개발환경!!!
			 * return false;*/
			 
		}
	}
}
