package ua.in.leopard.androidCoocooAfisha;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class DataCollector {
	private final Context myContext;
	
	public DataCollector(Context context) {
		this.myContext = context;
	}
	
	public void getTheatersData(){
		this.getTheatersDataFromJSON(JsonClient.getData(EditPreferences.getTheaterUrl(this.myContext)));
	}
	
	public void getCinemasData(){
		this.getCinemasDataFromJSON(JsonClient.getData(EditPreferences.getCinemasUrl(this.myContext)));
	}
	
	public void getTheatersDataFromJSON(JSONObject js_obj){
		try {
			JSONArray theaters_array = js_obj.getJSONArray("theaters");
			if (theaters_array != null){
				DatabaseHelper DatabaseHelperObject = new DatabaseHelper(this.myContext);
				int city_id = Integer.parseInt(EditPreferences.getCityId(this.myContext));
				for (int i = 0; i < theaters_array.length(); ++i) {
				    JSONObject row = theaters_array.getJSONObject(i);
				    if (city_id == row.getInt("city_id")){
				    	
				    	DatabaseHelperObject.setTheater(new TheaterDB(
				    			row.getInt("id"), city_id, 
				    			row.getString("title"), 
				    			row.getString("link"), 
				    			row.getString("address"), 
				    			row.getString("phone"))
				    	);
				    }
				}
			}
		} catch (JSONException e) {
			Log.i("dataCollector", "error data");
			e.printStackTrace();
		}
	}
	
	public void getCinemasDataFromJSON(JSONObject js_obj){
		try {
			JSONArray cinemas_array = js_obj.getJSONArray("cinemas");
			if (cinemas_array != null){
				DatabaseHelper DatabaseHelperObject = new DatabaseHelper(this.myContext);
				for (int i = 0; i < cinemas_array.length(); ++i) {
				    JSONObject row = cinemas_array.getJSONObject(i);
				    if (row != null){
				    	DatabaseHelperObject.setCinema(new CinemaDB(
				    			row.getInt("id"), 
				    			row.getString("title"),
				    			row.getString("orig_title"), 
				    			row.getString("year"), 
				    			row.getString("poster"), 
				    			row.getString("description"))
				    	);
				    }
				}
			}
			
			JSONArray afisha_array = js_obj.getJSONArray("afisha");
			if (afisha_array != null){
				DatabaseHelper DatabaseHelperObject = new DatabaseHelper(this.myContext);
				for (int i = 0; i < afisha_array.length(); ++i) {
				    JSONObject row = afisha_array.getJSONObject(i);
				    if (row != null){
				    	DatabaseHelperObject.setAfisha(new AfishaDB(
				    			row.getInt("id"), 
				    			row.getInt("cinema_id"), 
				    			row.getInt("theater_id"), 
				    			row.getString("zal_title"),
				    			row.getString("date_begin"), 
				    			row.getString("date_end"), 
				    			row.getString("times"), 
				    			row.getString("prices"))
				    	);
				    }
				}
			}
		} catch (JSONException e) {
			Log.i("dataCollector", "error data");
			e.printStackTrace();
		}
	}
}
