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
 * 1.0	     leijiang2    2018/6/2114:10	  Create	
 */
package com.iflytek.ivr.httpclient;

import com.google.common.net.MediaType;
import com.iflytek.ivr.inter.Command;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class HttpCommand implements Command {

    public CloseableHttpClient httpClient;

    public HttpUriRequest httpUriRequest;

    public HttpRequest httpRequest;

    public HttpCommand(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public CloseableHttpResponse execute() {
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpUriRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    public static HttpCommand create(HttpRequest request) {
        HttpCommand httpCommand = new HttpCommand(request);
        httpCommand.initial();
        return httpCommand;
    }

    private void initial() {
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(populateQueryParams(httpRequest));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        this.httpClient = HttpClientFactory.getClient();
        try {
            switch (httpRequest.getHttpmethod()){
                case get:
                    this.httpUriRequest = new HttpGet(uriBuilder.build());
                    break;
                case post:
                    this.httpUriRequest = new HttpPost(uriBuilder.build());
                    break;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        httpUriRequest.addHeader("Accept", MediaType.JSON_UTF_8.toString());
        populateHeaders(httpRequest);
        if (httpRequest.isHasJson()) {
            httpUriRequest.addHeader("Content-Type", MediaType.JSON_UTF_8.toString());
            EntityBuilder builder = EntityBuilder.create().setContentType(
                    ContentType.APPLICATION_JSON).setText(httpRequest.getJson());
            if (builder != null && httpUriRequest instanceof HttpEntityEnclosingRequestBase) {
                ((HttpEntityEnclosingRequestBase) httpUriRequest).setEntity(builder.build());
            }
        }
    }

    private URI populateQueryParams(HttpRequest request) throws URISyntaxException {

        URIBuilder uri = new URIBuilder(request.getEndPoint());

        if (!request.hasQueryParam())
            return uri.build();

        for (Map.Entry<String, List<Object>> entry : request.getQueryParams().entrySet()) {
            for (Object o : entry.getValue()) {
                uri.addParameter(entry.getKey(), String.valueOf(o));
            }
        }
        return uri.build();
    }

    private void populateHeaders(HttpRequest request) {
        if (!request.hasHeaders())
            return;

        for (Map.Entry<String, Object> h : request.getHeaders().entrySet()) {
            httpUriRequest.addHeader(h.getKey(), String.valueOf(h.getValue()));
        }
    }

}
