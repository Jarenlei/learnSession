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
 * 1.0	     leijiang2    2018/6/2114:15	  Create	
 */
package com.iflytek.ivr.httpclient;

import com.iflytek.ivr.util.LogUtil;

public class HttpExecutor {

    private static final HttpExecutor INSTANCE = new HttpExecutor();
    private HttpExecutorService service;

    private HttpExecutor() {}

    private HttpExecutorService service() {
        if (service != null) return service;

        return service = new HttpExecutorServiceImpl();
    }

    public static HttpExecutor create() {
        return INSTANCE;
    }

    public String getExecutorName() {
        return service().getExecutorDisplayName();
    }

    /**
     * Delegate to {@link HttpExecutorService#execute(HttpRequest)}
     */
    public HttpResponse execute(HttpRequest request) {
        LogUtil.debug("Executing Request: " + request.getEndPoint());
        return service().execute(request);
    }

}
