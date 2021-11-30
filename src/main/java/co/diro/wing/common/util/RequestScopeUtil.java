package co.diro.wing.common.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import co.diro.wing.common.vo.LoginUserInfo;

public class RequestScopeUtil {
	
	public static LoginUserInfo getLoginUserInfo() {
        return (LoginUserInfo) RequestContextHolder.getRequestAttributes().getAttribute("loginUserInfo", RequestAttributes.SCOPE_REQUEST);
    }
	
    public static void setLoginUserInfo(LoginUserInfo loginUserInfo) {
        RequestContextHolder.getRequestAttributes().setAttribute("loginUserInfo", loginUserInfo, RequestAttributes.SCOPE_REQUEST);
    }	
	
    public static Object getAttribute(String key) {
        return (Object) RequestContextHolder.getRequestAttributes().getAttribute(key, RequestAttributes.SCOPE_REQUEST);
    }

    public static void setAttribute(String key, Object obj) {
        RequestContextHolder.getRequestAttributes().setAttribute(key, obj, RequestAttributes.SCOPE_REQUEST);
    }

    public static void removeAttribute(String key) {
        RequestContextHolder.getRequestAttributes().removeAttribute(key, RequestAttributes.SCOPE_REQUEST);
    }
    
}
