package com.imooc.MRAutoSpace.utils.http;


import com.imooc.MRAutoSpace.model.http.HttpClientRequest;
import com.imooc.MRAutoSpace.model.http.HttpClientResponse;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by apple on 2020/2/25.
 */
public class HttpClientUtil {
    private CloseableHttpClient httpClient;
    private static Logger logger = Logger.getLogger (HttpClientUtil.class);

    public static HttpClientResponse doGet(HttpClientRequest httpClientRequest){
        HttpClientUtil httpClientUtil = new HttpClientUtil ();
        httpClientUtil.init ();
        HttpGet httpGet = new HttpGet (httpClientRequest.getUrl ());
        return  httpClientUtil.sendRequest (httpGet,httpClientRequest);
    }

    public static HttpClientResponse doPost(HttpClientRequest httpClientRequest){
        HttpClientUtil httpClientUtil = new HttpClientUtil ();
        httpClientUtil.init ();
        HttpPost httpPost = new HttpPost (httpClientRequest.getUrl ());
        return  httpClientUtil.sendRequest (httpPost,httpClientRequest);
    }




    private void init(){
        httpClient = HttpClientBuilder.create().build();
        logger.info ("Start init http connection");

    }

    private void  close(){
        try {
            httpClient.close ();
            logger.info ("Close http connection successfully!");
        } catch (IOException e) {
           logger.error("close http connection failed");
           logger.error (e.getMessage ());
        }

    }

    private HttpClientResponse sendRequest(HttpRequestBase httpRequestBase, HttpClientRequest httpClientRequest){
        HttpClientResponse httpClientresponse = new HttpClientResponse ();
        String encodingOfBody = "ISO-8859-1";
        HttpClientUtil httpClientUtil = new HttpClientUtil();
        httpClientUtil.init ();

        Map<String,String> requestHeaders  = httpClientRequest.getHeaders ();
        for(String key:requestHeaders.keySet ()){
            httpRequestBase.setHeader (key,requestHeaders.get (key));
            if(requestHeaders.get (key).equals ("Content-type")){
                String contentType = requestHeaders.get (key);
                if(contentType.length () >=2){
                    encodingOfBody = contentType.split (";")[1].split ("=")[1];

                }


            }
        }

        try {
            if(httpRequestBase instanceof HttpEntityEnclosingRequestBase){
                ((HttpEntityEnclosingRequestBase)httpRequestBase).setEntity(new StringEntity (httpClientRequest.getBody ()));

            }
        } catch (UnsupportedEncodingException e) {
            logger.error("This encoding is not supported");
            logger.error (e.getMessage ());

        }

        try {
            CloseableHttpResponse response = httpClient.execute(httpRequestBase);
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
            this.close ( );

        } catch (IOException e) {
            logger.error (e.getMessage ());
        }
        return httpClientresponse;

    }

    public String formarUrl(String url,Map<String,String> queryParams){
        String result = "";
        String  params = "";
        for(String key:queryParams.keySet ()){
            params += key + "=" + queryParams.get (key)+"&";
        }

        result = url + "?"+ params.substring (0,params.length ()-1);
        return result;



    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

}
