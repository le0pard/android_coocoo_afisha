package ua.in.leopard.androidCoocooAfisha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonClient {
	
	public static JSONObject getData(String url){
		JSONObject return_data = null;
		HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("Accept", "application/json");
        httpget.setHeader("Content-type", "application/json");
        
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            // Examine the response status
            Log.i("JsonClient",response.getStatusLine().toString());
 
            // Get hold of the response entity
            HttpEntity entity = response.getEntity();
            // If the response does not enclose an entity, there is no need
            // to worry about connection release
 
            if (entity != null) {
                // A Simple JSON Response Read
                InputStream instream = entity.getContent();
                String result= convertStreamToString(instream);
                // A Simple JSONObject Creation
                return_data=new JSONObject(result);
                // Closing the input stream will trigger connection release
                instream.close();
            }

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.v("JsonClient","ClientProtocolException");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.v("JsonClient","IOException");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.v("JsonClient","JSONException");
        } catch (Exception e) { 
        	e.printStackTrace();
        	Log.v("JsonClient","Exception");
        }
        
        return return_data;
	}
	
	private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
	
}
