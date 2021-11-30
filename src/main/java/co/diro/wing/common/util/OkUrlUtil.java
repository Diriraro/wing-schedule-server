/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package co.diro.wing.common.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
public class OkUrlUtil {

  private static final int timeout = 3;

  private static OkHttpClient client = null;

  public static OkHttpClient getClient() {
    if (client == null) {
      client = new OkHttpClient.Builder()
              .connectTimeout(timeout , TimeUnit.SECONDS)
              .writeTimeout(timeout  * 10, TimeUnit.SECONDS)
              .readTimeout(timeout  * 10, TimeUnit.SECONDS)
              .build();
    }
    return client;
  }

  /**
   * get
   *
   * <pre>example :
   * Map h = new HashMap();
   * h.put("Content-type", "application/x-www-form-urlencoded");
   * h.put("Accept", "text/html,text/xml,application/xml");
   *
   * HttpResp resp = OkUrlUtil.get("http://localhost/item/?a=1&b1", h);
   * </pre>
   */
  public static HttpResponse get(String urladdr, String headerType, String headerValue) {
    Map<String,String> h = new HashMap<String,String>();
    h.put(headerType,headerValue);
    try  {
      return get(urladdr, h);
    } catch (Exception e) {
      return new HttpResponse(urladdr, 9999, e.getMessage());
    }
  }
  public static HttpResponse get(String urladdr, Map<String,String> headers) throws Exception {
    Headers headerbuild = Headers.of(headers);
    Request request = new Request.Builder().url(urladdr).get().headers(headerbuild).build();

    try (Response response = getClient().newCall(request).execute()) {
      String msg =  response.body().string();
      if (log.isDebugEnabled()) {
        log.debug("<<<{}" , msg);
      }
      return new HttpResponse(msg, response.code(), HttpStatus.valueOf(response.code()).getReasonPhrase());
    } catch (Exception e) {
      return new HttpResponse(urladdr, 9999, e.getMessage());
    }

  }

  public static HttpResponse put(String urladdr, Map<String,String> headers, String strJsonBody) throws Exception {

    if (log.isDebugEnabled()) {
      log.debug(">>>{}" , strJsonBody);
    }
    RequestBody body = null;
    try {
      MediaType JSON = MediaType.parse(headers.get(HttpHeaders.CONTENT_TYPE));
      JSON.charset(StandardCharsets.UTF_8);
      body = RequestBody.create(JSON, strJsonBody);
    } catch (NullPointerException npe) {

      return new HttpResponse("{\"resultCode\":4105, \"resultMsg\":\"UNSUPPORTED MEDIA TYPE\"}", 4105, npe.getMessage());
    }

    if (body != null) {
      Headers headerbuild = Headers.of(headers);
      Request request = new Request.Builder().url(urladdr).put(body).headers(headerbuild).build();

      try (Response response = getClient().newCall(request).execute()) {
        String msg =  response.body().string();
        if (log.isDebugEnabled()) {
          log.debug("<<<{}" , msg);
        }
        return new HttpResponse(msg, response.code(), response.message());
      } catch (Exception e) {
        return new HttpResponse(null, 9999, e.getMessage());
      }
    } else {
      return new HttpResponse("{\"resultCode\":4000, \"resultMsg\":\"BAD REQUEST\"}", 4000, "");
    }

  }
  public static HttpResponse patch(String urladdr, Map<String,String> headers, String strJsonBody) throws Exception {

    if (log.isDebugEnabled()) {
      log.debug(">>>{}" , strJsonBody);
    }
    RequestBody body = null;
    try {
      MediaType JSON = MediaType.parse(headers.get(HttpHeaders.CONTENT_TYPE));
      JSON.charset(StandardCharsets.UTF_8);
      body = RequestBody.create(JSON, strJsonBody);
    } catch (NullPointerException npe) {
      return new HttpResponse("{\"resultCode\":4105, \"resultMsg\":\"UNSUPPORTED MEDIA TYPE\"}", 4105, npe.getMessage());
    }
    if (body != null) {
      Headers headerbuild = Headers.of(headers);
      Request request = new Request.Builder().url(urladdr).patch(body).headers(headerbuild).build();

      try (Response response = getClient().newCall(request).execute()) {
        String msg =  response.body().string();
        if (log.isDebugEnabled()) {
          log.debug("<<<{}" , msg);
        }
        return new HttpResponse(msg, response.code(), response.message());
      } catch (Exception e) {
        return new HttpResponse(null, 9999, e.getMessage());
      }
    } else {
      return new HttpResponse("{\"resultCode\":4000, \"resultMsg\":\"BAD REQUEST\"}", 4000, "");
    }

  }

  public static HttpResponse post(String urladdr, Map<String,String> headers, String strJsonBody) throws Exception {

    if (log.isDebugEnabled()) {
      log.debug(">>>{}" , strJsonBody);
    }
    RequestBody body = null;
    try {
      MediaType JSON = MediaType.parse(headers.get(HttpHeaders.CONTENT_TYPE));
      JSON.charset(StandardCharsets.UTF_8);
      body = RequestBody.create(JSON, strJsonBody);
    } catch (NullPointerException npe) {
      return new HttpResponse("{\"resultCode\":4105, \"resultMsg\":\"UNSUPPORTED MEDIA TYPE\"}", 4105, npe.getMessage());
    }

    if (body != null) {
      Headers headerbuild = Headers.of(headers);
      Request request = new Request.Builder().url(urladdr).post(body).headers(headerbuild).build();

      try (Response response = getClient().newCall(request).execute()) {
        String msg =  response.body().string();
        if (log.isDebugEnabled()) {
          log.debug("<<<{}" , msg);
        }
        return new HttpResponse(msg, response.code(), response.message());
      } catch (Exception e) {
        return new HttpResponse(null, 9999, e.getMessage());
      }
    } else {
      return new HttpResponse("{\"resultCode\":4000, \"resultMsg\":\"BAD REQUEST\"}", 4000, "");
    }

  }

  public static HttpResponse delete(String urladdr, Map<String,String> headers) throws Exception {

    Headers headerbuild = Headers.of(headers);
    Request request = new Request.Builder().url(urladdr).delete().headers(headerbuild).build();

    try (Response response = getClient().newCall(request).execute()) {
      String msg =  response.body().string();
      if (log.isDebugEnabled()) {
        log.debug("<<<{}" , msg);
      }
      return new HttpResponse(msg, response.code(), response.message());
    } catch (Exception e) {
      return new HttpResponse(null, 9999, e.getMessage());
    }
  }

  public static boolean isExist(String urladdr, Map<String,String> headers) throws Exception {
    boolean bl = false;
    try {
      HttpResponse resp = get(urladdr, headers);
      if (resp.getStatusCode() >= 200 && resp.getStatusCode() <= 300) {
        bl = true;
      } else {
        bl = false;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return bl;
  }


} // end class
