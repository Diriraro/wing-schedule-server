package co.diro.wing.common.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;

import ch.qos.logback.classic.Logger;
import co.diro.wing.common.component.CommonComponent;
import co.diro.wing.common.exception.AuthException;
import co.diro.wing.common.exception.GlobalException;
import co.diro.wing.user.vo.UserTokenVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("jwtService")
public class JwtService extends CommonComponent{
	
	@Value("${jwt.secret}")
    private String SALT;
    
    public String create(String key, UserTokenVo data, String subject){
    	
    	try {
    		
    		String jwt = Jwts.builder()
    				.setHeaderParam("typ", "JWT")
    				.setHeaderParam("regDate", System.currentTimeMillis())
    				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60L * 60L ))
    				.setSubject(subject)
    				.claim(key, data)
    				.signWith(SignatureAlgorithm.HS256, this.generateKey())
    				.compact();
    		
    		return jwt;
			
		} catch (Exception e) {
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
        log.info("token value : "+value);
		return value;
	}

	public int getMemberId() {
		return (Integer) this.get("member").get("id");
	}

	public boolean isUsable(String jwt) throws Exception {
		try{
			String ckSub = jwt.substring(7); 
			Jws<Claims> claims = Jwts.parser()
					  .setSigningKey(this.generateKey())
					  .parseClaimsJws(ckSub);
			return true;
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new AuthException("재로그인이 필요합니다.");
		}
	}
	
	public String getUserId(HttpServletRequest request) throws Exception {
		String token = request.getHeader("Authorization");
		String id = "";
		try {
			if(token != null && this.isUsable(token)) {
				Map<String, Object> tokenMap = this.get("member");
				 id = tokenMap.get("userIdPk")+"";
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AuthException(e);
		}
		return id;
	}
}
