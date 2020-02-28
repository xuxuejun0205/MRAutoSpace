package com.imooc.MRAutoSpace.model.http;

import org.apache.http.client.methods.HttpUriRequest;

import java.util.Map;

/**
 * Created by apple on 2020/2/25.
 */
public class HttpClientRequest {
    private String url;
    private HttpUriRequest httpMethod;
    private Map<String,String> headers;
    private String body;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpUriRequest getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpUriRequest httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
