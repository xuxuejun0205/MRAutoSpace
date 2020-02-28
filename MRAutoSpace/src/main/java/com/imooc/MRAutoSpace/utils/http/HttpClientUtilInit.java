package com.imooc.MRAutoSpace.utils.http;


import com.imooc.MRAutoSpace.model.http.HttpClientRequest;
import com.imooc.MRAutoSpace.model.http.HttpClientResponse;
import com.sun.istack.internal.logging.Logger;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.print.attribute.standard.RequestingUserName;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple on 2020/2/24.
 */
public class HttpClientUtilInit {
    private static Logger logger = Logger.getLogger(HttpClientUtilInit.class);

    public void testMethod() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String url = "http://localhost:19090/getClassName?name=value";
        HttpGet get = new HttpGet(url);
        try {
            httpClient.execute(get);
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void testMethodPost() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String url = "http://localhost:19090/getClassName?name=value";
        HttpPost post = new HttpPost(url);
        post.setHeader("headerName","headerValue");
        try {
            post.setEntity(new StringEntity("post request body"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
         CloseableHttpResponse response = httpClient.execute(post);
           logger.info(response.getStatusLine().toString().split(" ")[1]);

            Header[] headers = response.getAllHeaders();
            for(Header header:headers){
                logger.info(header.getName()+":"+header.getValue());

            }

            HttpEntity entity = response.getEntity();
            String body = org.apache.commons.io.IOUtils.toString(entity.getContent());
            logger.info (body);
            httpClient.close ( );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public HttpClientResponse dopost(HttpClientRequest request){
        HttpClientResponse httpClientresponse = new HttpClientResponse ();
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost(request.getUrl ());
        Map<String,String> requestHeaders  = request.getHeaders ();
        for(String key:requestHeaders.keySet ()){
            post.setHeader (key,requestHeaders.get (key));
        }

        try {
            post.setEntity(new StringEntity (request.getBody ()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            CloseableHttpResponse response = httpClient.execute(post);
            String statusCode = response.getStatusLine().toString().split(" ")[1];
            logger.info(statusCode);
            httpClientresponse.setStatusCode (statusCode);

            Header[] headers = response.getAllHeaders();
            Map<String,String> responseHeaders = new HashMap<String, String> ();
            for(Header header:headers){
                logger.info(header.getName()+":"+header.getValue());
                responseHeaders.put (header.getName (),header.getValue ());

            }
            httpClientresponse.setHeaders (responseHeaders);

            HttpEntity entity = response.getEntity();
            String body = org.apache.commons.io.IOUtils.toString(entity.getContent());
            logger.info (body);
            httpClientresponse.setBody (body);
            httpClient.close ( );

        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpClientresponse;

    }


    public static void main(String[] args){
        HttpClientUtilInit util = new HttpClientUtilInit ();
       //util.testMethod();
        //util.testMethodPost();
        HttpClientRequest request = new HttpClientRequest ();
        request.setUrl ("http://localhost:19090/getClassName?name=value");
        Map<String,String> requestHeaders = new HashMap<String, String> ();
        requestHeaders.put ("name01","value01");
        requestHeaders.put ("name02","value02");
        request.setHeaders (requestHeaders);
        request.setBody ("post request body");
        util.dopost (request);


    }
}
