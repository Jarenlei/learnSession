/* 
 *
 * Copyright (C) 1999-2014 IFLYTEK Inc.All Rights Reserved. 
 * 
 * FileName：VIE60
 * 
 * Description：
 * 
 * History：
 * Version   Author      Date            Operation 
 * 1.0	     jwyuan    2016/1/2610:25	  Create	
 */
package com.iflytek.vie.threadpool.callback;

import com.google.common.util.concurrent.FutureCallback;

/**
 * Created by jwyuan on 2016/1/26.
 */
public class ProcessCallBack implements FutureCallback {
    @Override
    public void onSuccess(Object o) {
        System.out.println("success");
    }

    @Override
    public void onFailure(Throwable throwable) {
        System.out.println("fail");
    }
}
