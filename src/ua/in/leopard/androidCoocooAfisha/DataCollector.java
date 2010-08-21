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
	
	public void getTheatersDataFromJSON(JSONObject js_obj){
		try {
			JSONArray theaters_array = js_obj.getJSONArray("theaters");
			if (!theaters_array.equals(null)){
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
}
