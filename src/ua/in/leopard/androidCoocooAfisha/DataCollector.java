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
		this.getDataFromJSON(JsonClient.getData(EditPreferences.getTheaterUrl(this.myContext)));
	}
	
	public void getDataFromJSON(JSONObject js_obj){
		try {
			JSONArray theaters_array = js_obj.getJSONArray("theaters");
			if (!theaters_array.equals(null)){
				for (int i = 0; i < theaters_array.length(); ++i) {
				    JSONObject row = theaters_array.getJSONObject(i);
				    
				    int id = row.getInt("id");
				    String title = row.getString("title");
				    Log.i("dataCollector", "id: " + Integer.toString(id) + ", title: " + title);
				}
			}
		} catch (JSONException e) {
			Log.i("dataCollector", "error data");
			e.printStackTrace();
		}
	}
}
