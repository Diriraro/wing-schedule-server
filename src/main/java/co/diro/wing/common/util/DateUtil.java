/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package co.diro.wing.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author shaby
 *
 */
/**
 * @author shaby
 *
 */
@Slf4j
public class DateUtil {

  public static enum DateCode {
    IS_AFTER("C001"), IS_EQUAL("C002"), IS_BEFORE("C003"), IS_ETC("C999");

    private String code;

    private DateCode(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }

    public static DateCode parseCode(String code) {
      for (DateCode entityType : values()) {
        if (entityType.getCode().equals(code)) {
          return entityType;
        }
      }
      return null;
    }
  }

  /**
   * 현재 시간 ISO 8601 format으로 리턴(24시간제) yyyy-MM-dd'T'HH:mm:ss\
   *
   * <p>
   * return value : 2018-12-12T17:18:00
   */
  public static String getTime() {
    ZoneOffset zo = ZonedDateTime.now().getOffset();
    return OffsetDateTime.of(LocalDateTime.now(), zo).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss,SSSXXX")).toString();
  }

  /**
   * 패턴으로 시간 가져오기
   *
   * <p>
   * <code>DateUtil.getTime("yyyy-MM-dd HH:mm:ss")</code>
   * <p>
   * return value : 2018-12-12T17:18:00
   */
  public static String getTime(String pattern) {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
  }

  /**
   * LocalDateTime.now() -> Timestamp 컨버트
   *
   * <p>
   * return value : 2018-12-12T17:18:00
   */
  public static Timestamp getTimestamp() {
    return Timestamp.valueOf(LocalDateTime.now());
  }


  /**
   * ISO TIME 로 변경
   * <p>
   * time : 2018-12-12 17:18:00 , 2018-12-12 , 2018-12-12 01:20
   * <p>
   * <code>DateUtil.getISOTime("2018-12-12 17:18")</code>
   * <p>
   * return value : 2018-12-12T17:18:00
   */
  public static String getISOTime(String time) {
    String sDate = "";
    String atime = "";
    ZoneOffset zo = ZonedDateTime.now().getOffset();
    try {
      LocalDateTime _date = null;
      if (time != null && !"".equals(time)) {
        atime = time.replaceAll("[^0-9]", "");

        if (atime.length() == 8) {
          _date = LocalDate.parse(atime, DateTimeFormatter.ofPattern("yyyyMMdd")).atStartOfDay();
        } else if (atime.length() == 10) {
          _date = LocalDateTime.parse(atime, DateTimeFormatter.ofPattern("yyyyMMddHH"));
        } else if (atime.length() == 12) {
          _date = LocalDateTime.parse(atime, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        } else if (atime.length() == 14) {
          _date = LocalDateTime.parse(atime, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        } else if (atime.length() > 14  && atime.length() <= 17 ) {
          DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyyMMddHHmmss").appendValue(ChronoField.MILLI_OF_SECOND, 3).toFormatter();
          _date = LocalDateTime.parse(atime, formatter);
        } else {
          _date = LocalDateTime.parse(atime, DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSS"));
        }
        sDate = OffsetDateTime.of(_date, zo).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss,SSSXXX")).toString();

      } else {
        sDate = null;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return sDate;
  }

  /**
   * ISO TIME 로 변경
   * <p>
   * time : 2018-12-12 17:18:00 , 2018-12-12 , 2018-12-12 01:20
   * <p>
   * <code>DateUtil.getISOTime("2018-12-12 17:18", "yyyy-MM-dd")</code>
   * <p>
   * return value : 2018-12-12T17:18:00
   */
  public static String getISOTime(String time, String pattern) {
    String sDate = "";
    try {
      time = time.replaceAll("[^0-9]", "");

      LocalDateTime _date = null;
      if (time.length() == 8) {
        _date = LocalDate.parse(time, DateTimeFormatter.ofPattern("yyyyMMdd")).atStartOfDay();
        sDate = _date.format(DateTimeFormatter.ofPattern(pattern)).toString();
      } else if (time.length() == 10) {
        _date = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyyMMddHH"));
        sDate = _date.format(DateTimeFormatter.ofPattern(pattern)).toString();
      } else if (time.length() == 12) {
        _date = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        sDate = _date.format(DateTimeFormatter.ofPattern(pattern)).toString();
      } else if (time.length() == 14) {
        _date = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        sDate = _date.format(DateTimeFormatter.ofPattern(pattern)).toString();
      } else if (time.length() == 17) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyyMMddHHmmss").appendValue(ChronoField.MILLI_OF_SECOND, 3).toFormatter();
        _date = LocalDateTime.parse(time, formatter);
        sDate = _date.format(DateTimeFormatter.ofPattern(pattern)).toString();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sDate;
  }
  public static LocalDateTime getLocalDateTime(String time) {
    LocalDateTime _date = null;
    String atime = "";
    ZoneOffset zo = ZonedDateTime.now().getOffset();
    try {
      if (time != null && !"".equals(time)) {
        atime = time.replaceAll("[^0-9]", "");

        if (atime.length() == 8) {
          _date = LocalDate.parse(atime, DateTimeFormatter.ofPattern("yyyyMMdd")).atStartOfDay();
        } else if (atime.length() == 10) {
          _date = LocalDateTime.parse(atime, DateTimeFormatter.ofPattern("yyyyMMddHH"));
        } else if (atime.length() == 12) {
          _date = LocalDateTime.parse(atime, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        } else if (atime.length() == 14) {
          _date = LocalDateTime.parse(atime, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        } else if (atime.length() > 14 && atime.length() <= 17) {
          DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyyMMddHHmmss").appendValue(ChronoField.MILLI_OF_SECOND, 3).toFormatter();
          _date = LocalDateTime.parse(atime, formatter);
        } else {
          DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyyMMddHHmmss").appendValue(ChronoField.NANO_OF_SECOND, 6).toFormatter();
          _date = LocalDateTime.parse(atime, formatter);
          //_date = LocalDateTime.parse(atime, DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSS"));
        }
        //sDate = OffsetDateTime.of(_date, zo).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss,SSSXXX")).toString();

      } else {
        //sDate = null;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return _date;
  }


  /**
   * 현재 시간 가져오기
   *
   */
  public static int getHour() {
    return LocalTime.now().getHour();
  }

  /**
   * 현재 분 가져오기
   *
   */
  public static int getMinute() {
    return LocalTime.now().getMinute();
  }

  /**
   * 현재 초 가져오기
   *
   */
  public static int getSecond() {
    return LocalTime.now().getSecond();
  }


  /**
   * 현재 일로 부터 날짜를 더하거나 빼서 yyyyMMdd 으로 데이터 가져오기
   * <p>
   * day : +- Ingeter.
   * <p>
   * <code>DateUtil.addDay(-1)</code>
   */
  public static String addDay(int dayNumber) {
    return LocalDate.now().plus(dayNumber, ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
  }

  /**
   * 현재 일자 기준으로 날짜를 더하거니 빼거나 합니다.
   *
   * <p>
   * pattern : yyyyMMdd , yyyyMMdd HH:mm.
   * <p>
   * dateType : ChronoUnit.YEARS, ChronoUnit.MONTHS ,ChronoUnit.DAYS , ChronoUnit.HOURS, ChronoUnit.MINUTES, ChronoUnit.SECONDS
   * <p>
   * num : +- Ingeter.
   *
   * <pre>
   * {@code
   *DateUtil.addDate(ChronoUnit.MINUTES, -40, "yyyyMMdd" )
   *DateUtil.addDate(ChronoUnit.MINUTES, -40, "HHmm" )
   * }
   * </pre>
   */
  public static String addDate(ChronoUnit dateType, int num, String pattern) {
    return LocalDateTime.now().plus(num, dateType).format(DateTimeFormatter.ofPattern(pattern));
  }

  /**
   * 임의의 시간에서 더하고 빼서 정해진 패턴으로 시간을 가져오기
   *
   * <p>
   * pattern : yyyyMMdd , yyyyMMdd HH:mm.
   * <p>
   * dateType : ChronoUnit.YEARS, ChronoUnit.MONTHS ,ChronoUnit.DAYS , ChronoUnit.HOURS, ChronoUnit.MINUTES, ChronoUnit.SECONDS
   * <p>
   * num : +- Ingeter.
   *
   * <pre>
   * {@code
   *DateUtil.addDate("2018-06-06 18:10", ChronoUnit.MINUTES, -40, "yyyyMMdd" )
   *DateUtil.addDate("2018-06-06 18:10", ChronoUnit.MINUTES, -40, "HHmm" )
   * }
   * </pre>
   */
  public static String addDate(String time, ChronoUnit dateType, int num, String pattern) {
    LocalDateTime _date = null;
    try {
      time = time.replaceAll("[^0-9]", "");
      if (time.length() == 8) {
        _date = LocalDate.parse(time, DateTimeFormatter.ofPattern("yyyyMMdd")).atStartOfDay();
      } else if (time.length() == 10) {
        _date = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyyMMddHH"));
      } else if (time.length() == 12) {
        _date = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
      } else if (time.length() == 14) {
        _date = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
      } else if (time.length() > 14) {
        time = time.substring(0, 14);
        _date = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return _date.plus(num, dateType).format(DateTimeFormatter.ofPattern(pattern)).toString();
  }

  public static ChronoUnit getChronoUnit(String str) {
    ChronoUnit cu = null;
    str = str.toUpperCase();
    switch (str) {
      case "YEAR":
        cu = ChronoUnit.YEARS;
        break;
      case "MONTH":
        cu = ChronoUnit.MONTHS;
        break;
      case "DAY":
        cu = ChronoUnit.DAYS;
        break;
      case "HOUR":
        cu = ChronoUnit.HOURS;
        break;
      case "MINUTE":
        cu = ChronoUnit.MINUTES;
        break;
      case "SECONDE":
        cu = ChronoUnit.SECONDS;
        break;
      default:
        cu = ChronoUnit.DAYS;
        break;
    }
    return cu;
  }

  public static boolean isPatternDate(String str) {
    boolean bl = false;
    if ("yyyy-MM-dd".equals(str)) {
      bl = true;
    } else if ("yyyyMMdd".equals(str)) {
      bl = true;
    } else if ("HHmm".equals(str)) {
      bl = true;
    } else if ("HH:mm".equals(str)) {
      bl = true;
    } else if ("yyyyMMddHH".equals(str)) {
      bl = true;
    } else if ("yyyyMMddHHmm".equals(str)) {
      bl = true;
    }
    return bl;
  }
  
  public static boolean isParseDate(String str) {
	boolean bl = true;
	SimpleDateFormat yymmdd = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat yymmddhhmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	yymmdd.setLenient(false);
	yymmddhhmmss.setLenient(false);
	try {
		yymmdd.parse(str);
	} catch (ParseException e) {
		try {
			yymmddhhmmss.parse(str);
		} catch (ParseException e1) {
			bl = false;
		}
	}
	return bl;
  }

  /**
   * 현재 일로 부터 날짜를 더하거나 빼서 yyyyMMdd 으로 데이터 가져오기
   * <p>
   * {@code DateUtil.getDate()}
   */
  public static String getDate() {
    return getDate("yyyyMMdd");
  }

  /**
   * 현재 일로 부터 날짜를 더하거나 빼서 패턴에 맞게 데이터를 가져온다
   *
   * <p>
   * <code>DateUtil.getDate("yyyyMMdd HH:mm")</code>
   */
  public static String getDate(String pattern) {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
  }

  public static int compareDate(String raw, long prevTime) {
    LocalDateTime _date = LocalDateTime.parse(raw, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    LocalDateTime comparedate = LocalDateTime.now().minus(prevTime, ChronoUnit.MINUTES);
    if (log.isDebugEnabled()) {
      //  log.debug("{} < {} = {}" , _date ,comparedate, comparedate.compareTo(_date));
    }
    return comparedate.compareTo(_date);
  }

  public static DateCode compareTo(String baseDate, String compareDate) {
    LocalDateTime date1 = LocalDateTime.parse(DateUtil.getISOTime(baseDate, "yyyyMMddHHmmssSSS"),
        new DateTimeFormatterBuilder().appendPattern("yyyyMMddHHmmss").appendValue(ChronoField.MILLI_OF_SECOND, 3).toFormatter());
    LocalDateTime date2 = LocalDateTime.parse(compareDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS"));
    if (date2.isAfter(date1)) {
      return DateCode.IS_AFTER;
    } else if (date2.isEqual(date1)) {
      return DateCode.IS_EQUAL;
    } else if (date2.isBefore(date1)) {
      return DateCode.IS_BEFORE;
    } else {
      return DateCode.IS_ETC;
    }
  }

  public static boolean isAfter(String baseDate, String compareDate) {
    LocalDateTime bDate = LocalDateTime.parse(DateUtil.getISOTime(baseDate, "yyyyMMddHHmmssSSS"),
        new DateTimeFormatterBuilder().appendPattern("yyyyMMddHHmmss").appendValue(ChronoField.MILLI_OF_SECOND, 3).toFormatter());
    LocalDateTime cDate = LocalDateTime.parse(compareDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS"));
    return cDate.isAfter(bDate);
  }
  public static boolean isBefore(String baseDate, String compareDate) {
    LocalDateTime bDate = LocalDateTime.parse(DateUtil.getISOTime(baseDate, "yyyyMMddHHmmssSSS"),
        new DateTimeFormatterBuilder().appendPattern("yyyyMMddHHmmss").appendValue(ChronoField.MILLI_OF_SECOND, 3).toFormatter());
    LocalDateTime cDate = LocalDateTime.parse(compareDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS"));
    return cDate.isBefore(bDate);
  }
  public static boolean isEqual(String baseDate, String compareDate) {
    LocalDateTime bDate = LocalDateTime.parse(DateUtil.getISOTime(baseDate, "yyyyMMddHHmmssSSS"),
        new DateTimeFormatterBuilder().appendPattern("yyyyMMddHHmmss").appendValue(ChronoField.MILLI_OF_SECOND, 3).toFormatter());
    LocalDateTime cDate = LocalDateTime.parse(compareDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS"));
    return cDate.isEqual(bDate);
  }


  /**
   * ISO 날짜 (yyyy-MM-dd'T'HH:mm:ss,SSSZ)를  yyyy-MM-dd HH:mm:ss,SSS 반환
   * @param isoTime
   * @return yyyy-MM-dd HH:mm:ss,SSS
   */
  public static String isoTimeToDataTime(String isoTime) {
    DateTimeFormatter format = new DateTimeFormatterBuilder()
        // date/time
        .append(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss,SSS"))
        // offset (hh:mm - "+00:00" when it's zero)
        .optionalStart().appendOffset("+HH:MM", "+00:00").optionalEnd()
        // offset (hhmm - "+0000" when it's zero)
        .optionalStart().appendOffset("+HHMM", "+0000").optionalEnd()
        // offset (hh - "Z" when it's zero)
        .optionalStart().appendOffset("+HH", "Z").optionalEnd()
        // create formatter
        .toFormatter();
    OffsetDateTime odt = OffsetDateTime.parse( isoTime, format);
    DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss,").appendValue(ChronoField.MILLI_OF_SECOND, 3).toFormatter();
    return odt.format(formatter);
  }
  /**
   * <pre>날짜 문자열 미리세컨드 변환</pre>
   * @Author      : hk-lee
   * @Date        : 2020. 6. 11.
   * @param dateString
   * @return : long
   */
  public static long getMillisecond(String dateString) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	long millis = 0;
	try {
		Date date;
		date = sdf.parse(dateString);
		millis = date.getTime();
	} catch (ParseException e) {
		 e.printStackTrace();
	}
	return millis;
  }
  
  public static long getMillisecond(String dateString, String pattern) {
	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
	long millis = 0;
	try {
		Date date;
		date = sdf.parse(dateString);
		millis = date.getTime();
	} catch (ParseException e) {
		 e.printStackTrace();
	}
	return millis;
  }  
  /**
   * <pre>밀리세컨드 -> Date객체 변환
   * yyyy-MM-dd'T'HH:mm:ss
   * </pre>
   * @Author      : hk-lee
   * @Date        : 2020. 6. 11.
   * @param mills
   * @return : String
   */
  public static String millsecondToDate(long mills) {
	// convert millis value to a timezone
	  Instant instant = Instant.ofEpochMilli(mills);
	  ZonedDateTime z = instant.atZone(ZoneId.of("Asia/Seoul"));
	  // format it
	  DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	  return fmt.format(z);
  }
  
  /**
   * <pre>밀리세컨드 -> Date String 변환 </pre>
   * @Author      : heenim
   * @Date        : 2020. 6. 12. 
   * @param mills
   * @param format
   * @return String
   */
  public static String millsecondToDate(Object oMills, String format) {
	  long mills = 0L;
	  if (oMills == null || oMills == "") { return null; }
	  try {
		  if (oMills instanceof Double) {
			  Double d = (Double)oMills;
			  mills = d.longValue();
		  }
		  else if (oMills instanceof Long) {
			  mills = (Long)oMills;
		  }		  
		  else {
			  mills = Long.parseLong(String.valueOf(oMills));
		  }
	  }
	  catch(NumberFormatException e) {
		  return null;
	  }
	  // convert millis value to a timezone
	  Instant instant = Instant.ofEpochMilli(mills);
	  ZonedDateTime z = instant.atZone(ZoneId.of("Asia/Seoul"));
	  // format it
	  DateTimeFormatter fmt = DateTimeFormatter.ofPattern(format);
	  return fmt.format(z);
  }   
  
  /**
	 * 날짜형식 문자열을 숫자제외한 문자를 제거하고 yyyyMMddhhmmss 형태로 만들어서 반환
	 * @param dt
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static String cleanDateStr(String dt) throws IllegalArgumentException {
		if (StringUtil.isEmpty(dt)) { return null; };
		
		String str = dt;
		str = str.replaceAll("[^0-9]","");
		if (str.length() >= 14) {
			str = str.substring(0, 14); 
		}
		else if (str.length() >= 8 && str.length() < 14) {
			str = StringUtils.rightPad(str, 14, "0");
		}
		else {
			throw new IllegalArgumentException("[" + dt + "] 날짜 형식 문자열이 아닙니다.");
		}
		return str;
	}
	
	/**
	 * 현재일과 비교하여 today > dt : 1, today == dt : 0, today < dt : -1 반환 
     * @Author      : zozi
     * @Date        : 2020. 12. 09. 
	 * @param dt
	 * @return
	 * @throws ParseException
	 */  
	public static int compareToToday(String dt) throws ParseException {
		int result = 0;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		String current = format.format(currentDate);
		Date today = format.parse(current);
		Date end = format.parse(dt);
		result = today.compareTo(end);
		return result;
	}
	
	/**
	 * 체류시간을 초단위로 계산하여 반환.
	 * @param startDt
	 * @param endDt
	 * @param pattern
	 * @return
	 */
	public static long getStayTime(String startDt, String endDt, String pattern) {
		long stayTime = 0;
		if (startDt != null && endDt != null) {
			long startTime = getMillisecond(startDt, pattern);
			long endTime   = getMillisecond(endDt, pattern);
			stayTime = ( endTime - startTime ) / 1000; // 초 단위
		}
		return stayTime;
	}
	
} // end class
