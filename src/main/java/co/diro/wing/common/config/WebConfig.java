package co.diro.wing.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import co.diro.wing.common.interceptor.JwtInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	private static final String[] EXCLUDE_PATHS = {
		"/",
		"/wingService/wingUserCheck",
		"/wingService/wingUserCreate",
		"/wingService/wingUserLogin"
	};
	
	@Autowired
    private JwtInterceptor jwtInterceptor;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedOrigins("http://localhost:3000")
		.allowedMethods("GET","POST","PUT","DELETE")
		.allowCredentials(true);
	}
 
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                        .addPathPatterns("/**")
                        .excludePathPatterns(EXCLUDE_PATHS);
    }

}
