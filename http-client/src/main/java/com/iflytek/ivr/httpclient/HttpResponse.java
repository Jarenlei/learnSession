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
 * 1.0	     leijiang2    2018/6/2114:09	  Create	
 */
package com.iflytek.ivr.httpclient;

import org.apache.http.client.methods.CloseableHttpResponse;

public class HttpResponse {

    private CloseableHttpResponse httpResponse;

    public HttpResponse(CloseableHttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    public static HttpResponse wrap(CloseableHttpResponse closeableHttpResponse){
        HttpResponse httpResponse = new HttpResponse(closeableHttpResponse);
        return httpResponse;
    }

    public int entityCode(){
        return httpResponse.getStatusLine().getStatusCode();
    }

}
