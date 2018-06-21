package com.iflytek.ivr.httpclient;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Creates the initial HttpClient and keeps it as a singleton to preserve pooling strategies within the Http Client
 *
 * @author Jeremy Unruh
 */
public class HttpClientFactory {

    private static final String USER_AGENT = "HttpClient-Agent";

    /**
     * Creates or Returns an existing HttpClient
     *
     * @return CloseableHttpClient
     */
    public static CloseableHttpClient getClient() {
        return buildClient();
    }

    private static CloseableHttpClient buildClient() {
        HttpClientBuilder cb = HttpClientBuilder.create().setUserAgent(USER_AGENT);
        RequestConfig.Builder rcb = RequestConfig.custom();
        rcb.setConnectTimeout(30 * 1000);
        rcb.setSocketTimeout(30 * 1000);
        return cb.setDefaultRequestConfig(rcb.build()).build();
    }
}
