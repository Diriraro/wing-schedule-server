package co.diro.wing.common.service;

import java.util.Map;

public interface JwtServicebackup {
	<T> String create(String key, T data, String subject);
	Map<String, Object> get(String key);
	int getMemberId();
	boolean isUsable(String jwt);
	
}