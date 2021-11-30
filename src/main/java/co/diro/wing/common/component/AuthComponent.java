package co.diro.wing.common.component;

import java.lang.reflect.Method;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.ReflectionUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import co.diro.wing.common.util.RequestScopeUtil;
import co.diro.wing.common.util.StringUtil;
import co.diro.wing.common.vo.CommonVo;
import co.diro.wing.common.vo.LoginUserInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class AuthComponent extends HttpComponent{
	
//	private String authServer;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	
	/**
	 * LoginUserInfo 에 저장된 로그인 사용자 정보를 가져와서 해당  Object ( VO extends CommonVO, Map ) 에 복사한다.
	 * @Author		: heenim
	 * @Date		: 2020. 4. 23. 
	 * @param userInfo
	 * @param obj
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })	
	public static void copyLoginUserInfoToObject(LoginUserInfo userInfo, Object obj) {	
		// long 기본값 설정.
		long loginAuthgroupOid = -1L;
		
		try {
			if (obj instanceof Map) { // map
				Map map = (Map) obj;				
				if (userInfo != null) {
					loginAuthgroupOid = StringUtil.isEmpty(userInfo.getAuthgroupOid()) ? -1L : Long.valueOf(userInfo.getAuthgroupOid());
					map.put("loginUserId"      , userInfo.getUserId());									// 사용자ID
					map.put("loginUserOrganCd" , userInfo.getOrganCd()); 								// 기관코드
					map.put("loginUserRegionId", userInfo.getRegionId());								// 지역ID (시도)
				}
				else {
					//map.put("loginUserId"      , "_NO_AUTH_");											// 사용자ID
				}
				map.put("loginAuthgroupOid", loginAuthgroupOid);										// 권한그룹ID
			}
			else { // vo
				if (userInfo != null) {		
					loginAuthgroupOid = StringUtil.isEmpty(userInfo.getAuthgroupOid()) ? -1L : Long.valueOf(userInfo.getAuthgroupOid());
					setFieldValue(obj, "loginUserId"      , userInfo.getUserId()  , String.class);		// 사용자ID
					setFieldValue(obj, "loginUserOrganCd" , userInfo.getOrganCd() , String.class);		// 기관코드
					setFieldValue(obj, "loginUserRegionId", userInfo.getRegionId(), String.class);		// 지역ID (시도)
				}
				else {
					//setFieldValue(obj, "loginUserId"      , "_NO_AUTH_"           , String.class);		// 사용자ID
				}
				setFieldValue(obj, "loginAuthgroupOid", loginAuthgroupOid, long.class);					// 권한그룹ID				
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Map 에 설정된 로그인 사용자 정보를 가져와서 해당 Object ( VO extends CommonVO, Map ) 에 복사한다.
	 * @Author		: heenim
	 * @Date		: 2020. 4. 23. 
	 * @param map
	 * @param obj
	 */
	@SuppressWarnings("rawtypes")
	public static void copyLoginUserInfoMapToObject(Map map, Object obj) {
		LoginUserInfo userInfo = new LoginUserInfo();
		userInfo.setUserId      (StringUtil.avoidNull(map.get("loginUserId"))); 		// 사용자ID
		userInfo.setOrganCd     (StringUtil.avoidNull(map.get("loginUserOrganCd"))); 	// 기관코드
		userInfo.setRegionId    (StringUtil.avoidNull(map.get("loginUserRegionId"))); 	// 지역ID (시도)
		userInfo.setAuthgroupOid(StringUtil.avoidNull(map.get("loginAuthgroupOid"))); 	// 권한그룹ID
		copyLoginUserInfoToObject(userInfo, obj);		
	}
	
	/**
	 * VO extends CommonVO 에 설정된 로그인 사용자 정보를 가져와서 해당 Object ( VO extends CommonVO, Map ) 에 복사한다.
	 * @param fromObj
	 * @param toObj
	 */
	public static void copyLoginUserInfoVoToObject(CommonVo fromObj, Object toObj) {
		LoginUserInfo userInfo = new LoginUserInfo();
		userInfo.setUserId      (StringUtil.avoidNull(fromObj.getLoginUserId())); 		// 사용자ID
		userInfo.setOrganCd     (StringUtil.avoidNull(fromObj.getLoginUserOrganCd())); 	// 기관코드
		userInfo.setRegionId    (StringUtil.avoidNull(fromObj.getLoginUserRegionId())); // 지역ID (시도)
		userInfo.setAuthgroupOid(StringUtil.avoidNull(fromObj.getLoginAuthgroupOid())); // 권한그룹ID
		copyLoginUserInfoToObject(userInfo, toObj);		
	}	
	
	/**
	 * Object (VO) 에 field 값을 설정.
	 * @Author		: heenim
	 * @Date		: 2020. 4. 23. 
	 * @param obj
	 * @param fieldName
	 * @param value
	 */
	private static void setFieldValue(Object obj, String fieldName, Object value, Class<?> valueType) {
		Class<?> clazz = obj.getClass();
		String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		Method method = ReflectionUtils.findMethod(clazz, methodName, new Class[] { valueType });
	    if (method != null) {
	    	ReflectionUtils.invokeMethod(method, obj, new Object[] { value });
		}
	}
	
	/**
	 * cache 가 되도록 AuthComponent 가져오기
	 * @return AuthComponent
	 */
	private AuthComponent getBeanProxy() {
		return applicationContext.getBean(AuthComponent.class);
	}
}
