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
 * 1.0	     leijiang2    2018/6/2113:38	  Create	
 */
package com.iflytek.ivr.util;

import org.apache.log4j.Logger;

public class LogUtil {

    /**
     * 获取日志Logger对象类
     */
    private final static Logger httpLogger = Logger.getLogger("studo");

    public static void info(Object message){
        if (httpLogger.isInfoEnabled()) {
            httpLogger.info(message);
        }
    }

    public static void debug(Object message){
        if (httpLogger.isDebugEnabled()) {
            httpLogger.debug(message);
        }
    }

    public static void error(Object message, Throwable t){
        httpLogger.error(message, t);
    }

}
