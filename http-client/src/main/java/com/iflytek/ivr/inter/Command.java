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
 * 1.0	     leijiang2    2018/6/2114:08	  Create	
 */
package com.iflytek.ivr.inter;

import org.apache.http.client.methods.CloseableHttpResponse;

public interface Command {

    CloseableHttpResponse execute();

}
