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

import org.apache.http.Header;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.Data;

@Data
public class HttpResponse {

  private String payload;
  private int statusCode;
  private String statusName;
  private Header[] reqHeaders;
  private Header[] resHeaders;

  public HttpResponse(String payload, int statusCode, String statusName, Header[] reqHeaders) {
    this(payload, statusCode, statusName, reqHeaders, null);
  }

  public HttpResponse(String payload, int statusCode, String statusName, Header[] reqHeaders, Header[] resHeaders) {
    this.payload = payload;
    this.statusCode = statusCode;
    this.statusName = statusName;
    this.reqHeaders = reqHeaders;
    this.resHeaders = resHeaders;
  }

  public HttpResponse(String payload, int statusCode, String statusName) {
    this.payload = payload;
    this.statusCode = statusCode;
    this.statusName = statusName;
  }

  public String getJSON() throws JSONException {
    JSONObject rtn = new JSONObject();
    rtn.put("resultCode", statusCode);
    rtn.put("resultMsg", statusName);
    rtn.put("payload", payload);
    return rtn.toString();
  }

} // end class
