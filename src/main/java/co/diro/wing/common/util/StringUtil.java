
package co.diro.wing.common.util;

import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
	public static final String EMPTY = "";
	
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str)) {
            return true;
        }
		return false;
	}
	
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	public static boolean equals(String str1, String str2) {
		return StringUtils.equals(str1, str2);
	}
	public static boolean equalsIgnoreCase(String str1, String str2) {
		return StringUtils.equalsIgnoreCase(str1, str2);
	}
	
	public static boolean notEquals(String str1, String str2) {
		return !StringUtils.equals(str1, str2);
	}
	
	public static String avoidNull(Object object) {
		return avoidNull(object, "");
	}
	
	public static String avoidNull(Object object, String string) {
		if (object != null) {
			string = object.toString().trim();
		}		
		return string;
	}

	public static boolean isEmptyByArray(String[] arr) {
		if (arr == null || arr.length == 0) return true;
		return false;
	}

	public static boolean isNull(Object obj) {
		
		if (obj == null) return true; 
			
		if (obj instanceof String) 
			return "".equals(obj.toString().trim());
		else if (obj instanceof List) 
			return ((List<?>) obj).isEmpty();
		else if (obj instanceof Map) 
			return ((Map<?, ?>) obj).isEmpty();
		else if (obj instanceof Object[]) 
			return Array.getLength(obj) == 0;
		else
			return true;
	}
	
	public static boolean isNotNull(Object obj) {
		return !isNull(obj);
	}
	
	/**
	 * <pre>로컬호스트명 조회</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 11. 7.
	 * @return
	 */
	public static String getLocalHostName(String defaultName) {
		String hostName = "";
		try {
			InetAddress local = InetAddress.getLocalHost();
			hostName = local.getHostName();
		} catch (UnknownHostException e) {
			hostName = defaultName;
		}
		return hostName;
	}

	// 날짜 형식 yyyyMMdd -> yyyy-MM-dd 로 변환
	public static String getParseDate(String strDate) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date date = null;

		String resultDate = "";
		try {
	        date = df.parse(strDate);
	        resultDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
	    }
	    catch (ParseException ex) { resultDate = ""; }

		return resultDate;
	}
	
	/**
	 * <pre>숫자문자열>실수형으로 변환</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 6. 15.
	 * @param object
	 * @return : double
	 */
	public static double parseNumber(Object object) {
		double result = 0;
		try {
			if (object instanceof Double) {
				result = (Double)object;
			}else if (object instanceof String) {
				result = Double.parseDouble((String)object);
			}else if (object instanceof Integer) {
				result = (Integer) object;
			}else if (object instanceof Long) {
				result = (Long) object;
			}
		}catch(Exception e) {
			e.printStackTrace();
			result = 0;
		}
		return result;
	}
	/**
	 * <pre>숫자문자열>실수형으로 변환</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 6. 15.
	 * @param object
	 * @return : double
	 */
	public static boolean parseBoolean(Object object) {
		boolean result = false;
		try {
			result = (boolean) object;
		}catch(Exception e) {
			if(object instanceof String) {
				result = Boolean.parseBoolean((String)object);
			}
		}
		return result;
	}
	/**
	 * <pre>숫자 유효성 체크</pre>
	 * @Author      : zozi
	 * @Date        : 2020. 9. 29.
	 * @param object
	 * @return : boolean
	 */
	public static boolean isNumber(Object object) {
		boolean result = false;
		try {
			Double.parseDouble((String) object);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 날짜 형식 yyyyMMdd -> yyyy-MM-dd 로 변환
	public static String getParseDateTime(String strDate) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date date = null;

		String resultDate = "";
		try {
	        date = df.parse(strDate);
	        resultDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
	    }
	    catch (ParseException ex) { resultDate = ""; }

		return resultDate;
	}
	
	public static boolean contains(String text, String searchText) {
		return StringUtils.contains(text, searchText);
	}

	public static boolean contains(List<String> textList, String searchText) {
		if (textList == null || textList.size() == 0 || isEmpty(searchText)) {
			return false;
		}
		for (String text : textList) {
			if (StringUtils.contains(text, searchText)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean containsOneOrMore(List<String> textList, String searchText) {
		if (textList == null || textList.size() == 0 || isEmpty(searchText)) {
			return false;
		}
		String[] arrSearch = searchText.split(",");
		for (String text : textList) {
			for (int i = 0; i < arrSearch.length; i++) {
				if (isNotEmpty(arrSearch[i]) && StringUtils.contains(text, arrSearch[i])) {
					return true;
				}
			}
		}
		return false;
	}	
}
