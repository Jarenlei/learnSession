package com.iflytek.ivr.httpclient;

import com.iflytek.ivr.util.LogUtil;
import org.apache.http.client.methods.CloseableHttpResponse;

/**
 * HttpExecutor is the default implementation for HttpExecutorService which is responsible for interfacing with HttpClient and mapping common status codes, requests and responses
 * back to the common API
 *
 * @author Jeremy Unruh
 */
public class HttpExecutorServiceImpl implements HttpExecutorService {

    private static final String NAME = "Apache HttpClient Connector";

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpResponse execute(HttpRequest request) {
        try {
            return invoke(request);
        }
        catch (Exception e) {
            LogUtil.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Invokes the given request
     *
     * @param request the request to invoke
     * @return the response
     * @throws Exception the exception
     */
    private HttpResponse invoke(HttpRequest request) throws Exception {
        HttpCommand command = HttpCommand.create(request);
        try {
            return invokeRequest(command);
        }catch (Exception pe) {
            pe.printStackTrace();
        }
        return null;
    }

    private HttpResponse invokeRequest(HttpCommand command) throws Exception {
        CloseableHttpResponse response = command.execute();
        response.close();
        return HttpResponse.wrap(response);
    }

    @Override
    public String getExecutorDisplayName() {
        return NAME;
    }
}
