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
 * 1.0	     leijiang2    2018/6/2111:23	  Create	
 */
package com.iflytek.ivr;

import com.iflytek.ivr.httpclient.HttpExecutorService;
import com.iflytek.ivr.httpclient.HttpExecutorServiceImpl;
import com.iflytek.ivr.httpclient.HttpRequest;
import com.iflytek.ivr.httpclient.HttpResponse;
import org.apache.log4j.Logger;

public class HttpRequestTest {

    private final Logger httpLogger = Logger.getLogger("studo");

    public static void main(String[] args) {
        HttpExecutorService httpExecutorService = new HttpExecutorServiceImpl();
        HttpRequest httpRequest = new HttpRequest.HttpRequestBuilder()
                .json("{\"callId\":\"testCallid\",\"errMsg\":\"success\",\"isError\":0}").method(
                        HttpRequest.HttpMethod.post).path("http://172.31.196.48:8080/silentSeat/crs/pullResult").toBuild();
        HttpResponse httpResponse = httpExecutorService.execute(httpRequest);
        System.out.println(httpResponse.entityCode());
    }

}
