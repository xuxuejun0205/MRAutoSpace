package com.imooc.MRAutoSpace.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imooc.MRAutoSpace.model.http.HttpClientRequest;
import com.imooc.MRAutoSpace.model.http.HttpClientResponse;
import com.imooc.MRAutoSpace.utils.http.HttpClientUtil;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by apple on 2020/2/25.
 */
public class HttpClientUtilsTest {
    private static Logger logger = Logger.getLogger (HttpClientUtilsTest.class);
    @Test
    public void testMethodGet(){
        HttpClientRequest request = new HttpClientRequest ();
        request.setUrl ("http://localhost:19090/getClassName?name=value");
        Map<String,String> requestHeaders = new HashMap<String, String> ();
        requestHeaders.put ("name01","value01");
        requestHeaders.put ("name02","value02");
        request.setHeaders (requestHeaders);

        HttpClientResponse response = HttpClientUtil.doGet (request);
        Assert.assertEquals ("200",response.getStatusCode ());
    }

    @Test
    public void testMethodPost(){
        HttpClientRequest request = new HttpClientRequest ();
        request.setUrl ("http://localhost:19090/getClassName?name=value");
        Map<String,String> requestHeaders = new HashMap<String, String> ();
        requestHeaders.put ("name01","value01");
        requestHeaders.put ("name02","value02");
        request.setHeaders (requestHeaders);
        request.setBody ("test body string");

        HttpClientResponse response = HttpClientUtil.doPost (request);
        Assert.assertEquals ("{\"id\": \"\", \"className\": \"testClassName\"}",response.getBody ());
    }

    @Test
    /*天天夺宝新增商品*/
    public void testMethodGoodsAdd(){
        HttpClientRequest request = new HttpClientRequest ();
        request.setUrl ("https://dailyapi.zmjx.com/marketing/seize/goods/add");

        Map<String,String> requestHeaders = new HashMap<String, String> ();
        requestHeaders.put ("Content-Type","application/json");
        requestHeaders.put ("token","120_690089690e61bf53ffda32a19a33f7bb");
        requestHeaders.put ("zmjx_client","4");
        request.setHeaders (requestHeaders);

        JSONObject object = new JSONObject ();
        object.put ("realPrize",1);
        object.put ("activityId",3);
        object.put ("couponTimes",5);
        object.put ("restart",1);
        object.put ("goodsPrice",200);
        object.put ("goodsTitle","IDEA");
        object.put ("sort",4);
        object.put ("goodsImgs","https://img.alicdn.com/imgextra/i3/2200548629280/O1CN01eQpC6N2IQI2xIclsi_!!2200548629280.jpg");
        object.put ("maxTimes",4);
        request.setBody (object.toJSONString (object));

        HttpClientResponse response = HttpClientUtil.doPost (request);

       //System.out.print (response.getBody ());

        JSONObject obj = JSON.parseObject (response.getBody ());
        Assert.assertEquals (true,obj.get ("status"));
    }

    @Test
    public void testFormatUrl(){
        String url = "http://localhost:19090/testurl";
        Map<String,String> requestHeaders = new HashMap<String, String> ();
        requestHeaders.put ("name01","value01");
        requestHeaders.put ("name02","value02");
        HttpClientUtil httpClientUtil = new HttpClientUtil ();
        //logger.info (httpClientUtil.formarUrl (url,requestHeaders));
        //System.out.println (httpClientUtil.formarUrl (url,requestHeaders));
        Assert.assertEquals (httpClientUtil.formarUrl (url,requestHeaders),"http://localhost:19090/testurl?name02=value02&name01=value01");



    }

}
