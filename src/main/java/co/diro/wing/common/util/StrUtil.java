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

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StrUtil {

  public String getFile(String fileName) {
    String templatePath = new CommonUtil().getJarPath();
    StringBuffer result = new StringBuffer("");
    File file = new File(templatePath + fileName);
    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        result.append(line);
      }
      scanner.close();
    } catch (IOException e) {
      log.error(e.getMessage());
    }
    return result.toString();
  }

  public static String[] strToArray(String msg, String div) {
    String[] mArr = null;
    if (!"".equals(msg.trim()) && msg != null) {
      if (msg.trim().indexOf(",") > 0) {
        mArr = msg.trim().split(",");
      } else if (msg.trim().indexOf(" ") > 0) {
        mArr = msg.trim().split(" ");
      } else {
        mArr = msg.trim().split(div);
      }
      return mArr;
    } else {
      return null;
    }
  }

  public static void arrayToList(List<String> Items, String[] a1) {
    try {
      for (String itm : a1) {
        if (!"".equals(itm)) {
          Items.add(itm);
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  public static String trim(Object object) {
    return object == null ? null : String.valueOf(object).trim();
  }

  public static String nvl(String val) {
    return nvl(val, "");
  }

  public static String nvl(String val, String replaceStr) {
    String _str = "";
    try {
      if (val == null) {
        _str = replaceStr;
      } else if ("null".equals(val) || "-".equals(val) || "".equals(val)) {
        _str = replaceStr;
      } else {
        _str = val;
      }
    } catch (Exception e) {
      _str = replaceStr;
    }
    return _str;
  }

  public static boolean isNull(String val) {
    boolean bl = false;
    try {
      if (val == null) {
        bl = true;
      } else if ("null".equals(val) || "-".equals(val) || "".equals(val)) {
        bl = true;
      }
    } catch (Exception e) {
      bl = false;
    }
    return bl;
  }

  public static String getCellString(int n) {
    char[] buf = new char[(int) java.lang.Math.floor(java.lang.Math.log(25 * (n + 1)) / java.lang.Math.log(26))];
    for (int i = buf.length - 1; i >= 0; i--) {
      n--;
      buf[i] = (char) ('A' + n % 26);
      n /= 26;
    }
    return new String(buf);
  }

  /**
   * Applies the specified mask to the card number.
   *
   * @param cardNumber The card number in plain format
   * @param mask       The number mask pattern. Use # to include a digit from the
   *                   card number at that position, use x to skip the digit at
   *                   that position
   *
   * @return The masked card number
   */
  public static String maskCardNumber(String cardNumber, String mask) {
    // format the number
    int index = 0;
    StringBuilder maskedNumber = new StringBuilder();
    for (int i = 0; i < mask.length(); i++) {
      char c = mask.charAt(i);
      if (c == '#') {
        maskedNumber.append(cardNumber.charAt(index));
        index++;
      } else if (c == 'x') {
        maskedNumber.append(c);
        index++;
      } else {
        maskedNumber.append(c);
      }
    }
    // return the masked number
    return maskedNumber.toString();
  }

  public static String lpad(String strContext, int iLen, String strChar ) {
    String strResult = "";
    StringBuilder sbAddChar = new StringBuilder();
    for ( int i = strContext.length(); i < iLen; i++ ) {
      // iLen길이 만큼 strChar문자로 채운다.
      sbAddChar.append( strChar );
    }
    strResult = sbAddChar + strContext; // LPAD이므로, 채울문자열 + 원래문자열로 Concate한다.
    return strResult;
  }
  public static String rpad(String strContext, int iLen, String strChar ) {
    String strResult = "";
    StringBuilder sbAddChar = new StringBuilder();
    for ( int i = strContext.length(); i < iLen; i++ ) {
      // iLen길이 만큼 strChar문자로 채운다.
      sbAddChar.append( strChar );
    }
    strResult = strContext + sbAddChar; // LPAD이므로, 채울문자열 + 원래문자열로 Concate한다.
    return strResult;
  }

} // end class
