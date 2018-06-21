/* 
 *
 * Copyright (C) 1999-2014 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：Temp
 * 
 * Description：
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0	     leijiang2    2018/6/2113:43	  Create	
 */
package com.iflytek.ivr.httpclient;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.http.entity.ContentType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequest {

    public enum HttpMethod{ post, get; }

    private HttpMethod httpmethod = HttpMethod.get;

    private String endPoint;

    private String contentType = ContentType.APPLICATION_JSON.toString();

    private String json;

    private boolean hasJson = false;

    private Map<String, Object> headers;

    private Map<String, List<Object>> queryParams;

    public boolean hasQueryParam() {
        return null != queryParams && !queryParams.isEmpty();
    }

    public boolean hasHeaders() {
        return null != headers && !headers.isEmpty();
    }

    public HttpMethod getHttpmethod() {
        return httpmethod;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public String getContentType() {
        return contentType;
    }

    public String getJson() {
        return json;
    }

    public boolean isHasJson() {
        return hasJson;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public Map<String, List<Object>> getQueryParams() {
        return queryParams;
    }

    public static class HttpRequestBuilder{

        HttpRequest request;

        public HttpRequestBuilder(){
            request = new HttpRequest();
        }

        public HttpRequestBuilder method(HttpMethod httpMethod){
            this.request.httpmethod = httpMethod;
            return this;
        }

        public HttpRequestBuilder path(String endPoint){
            this.request.endPoint = endPoint;
            return this;
        }

        public HttpRequestBuilder json(String json){
            this.request.hasJson = true;
            this.request.json = json;
            return this;
        }

        public HttpRequestBuilder queryParam(String key, Object value){
            if (null == this.request.queryParams) {
                this.request.queryParams = new HashMap<>();
            }
            List<Object> values = null;
            if (this.request.queryParams.containsKey(key)) {
                values = this.request.queryParams.get(key);
            } else {
                values = Lists.newArrayList();
            }
            values.add(value);
            return this;
        }

        public HttpRequestBuilder updateQueryParam(String key, Object value) {
            if (value == null)
                return this;

            if (request.queryParams == null)
                request.queryParams = Maps.newHashMap();

            List<Object> values = new ArrayList<Object>();
            values.add(value);
            request.queryParams.put(key, values);
            return this;
        }

        public HttpRequest toBuild(){
            return this.request;
        }
    }
}
