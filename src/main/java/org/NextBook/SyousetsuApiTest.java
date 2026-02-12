package org.NextBook;

import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
public class SyousetsuApiTest {
	
    public static JSONArray get(String url) {
    	
	    	String resultConent = "";
	    	JSONArray novels = null;
	    	HttpGet httpGet = new HttpGet(url);
	    	
	    	try (CloseableHttpClient httpclient = HttpClients.createDefault()){
	    		
	    		try (CloseableHttpResponse response = httpclient.execute(httpGet)){
	    			
	    			System.out.println(response.getVersion()); 		// HTTP/1.1
	                System.out.println(response.getCode()); // 200
	                System.out.println(response.getReasonPhrase()); // OK
	                HttpEntity entity = response.getEntity();
	                // Get response information
	                resultConent = EntityUtils.toString(entity);
	                novels = new JSONArray(resultConent);
	
	                for (int i = 1; i < novels.length(); i++) { 
	                    JSONObject novel = novels.getJSONObject(i);
	                    System.out.println("Title: " + novel.getString("title"));
	                    System.out.println("Author: " + novel.getString("writer"));
	                    
	                    break; 
	                }
	    		}catch (ParseException e) {
	    			e.printStackTrace();
	    		}
	    	}catch(IOException  e) {
	    		  e.printStackTrace();
	    	}
	    	return novels;
    }
}