package com.sourceSoft.digitalProjct.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * Http请求工具类
 * @author lvbj
 */
public class HttpUtils {  
	
	/**
	 * post请求
	 */
	public static String httpPost(String URL,String str) throws IOException{
		RestTemplate restTemplate = new RestTemplate();
		String retStr = restTemplate.postForObject(URL, str, String.class);
		return retStr;
	}
	
	/**
	 * get请求
	 */
	public static String httpGet(String URL) throws IOException{
		RestTemplate restTemplate = new RestTemplate();
		String retStr = restTemplate.getForObject(URL, String.class);
		return retStr;
	}
	
	public static void main(String[] args) {
		//终端升级反馈测试
		/*String url="http://192.168.7.52:8080/otaupgrade/ota_upgrade/upgradeSystemResult";*/
//		String url= "http://192.168.7.52:8080/otaip_upgrade/ota_upgrade/upgradeSystemResult";
////		String url= "http://192.168.5.64:8080/otaip_upgrade/ota_upgrade/upgradeSystemResult";
//		Map params = new HashMap();
//		String jsonData = "{\"stbId\":\"test111\"," +
//				"\"result\":{\"upgradeTaskId\":\"20140724100026\",\"upgradePackageId\":\"201407241000261013\"," +
//				"\"stbCurrentVer\":\"1.1.3\",\"upgradeResult\":\"0\"}}";
//		params.put("params", jsonData);
//		String msg = http(url, params);
//		System.out.println(msg);
		
//		//广告ID
		String adsId="9"; 
		//片源ID
		String movieId="-950430148"; 
		String adsName="广告测试9"; 
		//广告类型,1为视频，2为图片
		String adsType="2"; 
		//播放类型,1片头，2暂停,3片尾
		String playType="3";
		//广告地址
		String url="http://192.167.1.4:80/adstest/adidas.jpg";    
		//广告跳转地址
		String skipUrl="www.baidu.com"; 
		//广告播放时间,视频类为0
		String duration ="15";
		//---------------------------------------------
//		String httpUrl = "http://192.167.1.245/portal/apiAds/addAds?";
		String httpUrl = "http://10.10.6.2/portal/apiAds/addAds?";
		httpUrl += "adsId="+adsId;
		httpUrl += "&movieId="+movieId;
		httpUrl += "&adsName="+adsName;
		httpUrl += "&adsType="+adsType;
		httpUrl += "&playType="+playType;
		httpUrl += "&url="+url;
		httpUrl += "&skipUrl="+skipUrl;
		httpUrl += "&duration="+duration;
		System.out.println(httpUrl);
//		HttpUtils hu = new HttpUtils();
//		hu.httpGet(httpUrl);
		
	}
	
      
	public static String unicodeToString(String str2) {	        
		 Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");    	        
		 Matcher matcher = pattern.matcher(str2);	        
		 char ch;	       
		 while (matcher.find()) {	           
			 ch = (char) Integer.parseInt(matcher.group(2), 16);	            
			 str2 = str2.replace(matcher.group(1), ch + "");    	        
			 }	        
		 return str2;	    
	}
	
	public static String doPost(String url, String params) throws Exception {
		if(StringUtils.isEmpty(url) || StringUtils.isEmpty(params)){
			return null;
		}
        URL urlObj = new URL(url);
        URLConnection connection = urlObj.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
        
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpURLConnection.setRequestProperty("Content-Type", "text/plain;charset=utf-8");
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(params.length()));
        
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        
        try {
            outputStream = httpURLConnection.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream);
            
            outputStreamWriter.write(params);
            outputStreamWriter.flush();
            
            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
            
        } finally {
            
            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }
            
            if (outputStream != null) {
                outputStream.close();
            }
            
            if (reader != null) {
                reader.close();
            }
            
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            
            if (inputStream != null) {
                inputStream.close();
            }
            
        }

        return resultBuffer.toString();
    }
}     
