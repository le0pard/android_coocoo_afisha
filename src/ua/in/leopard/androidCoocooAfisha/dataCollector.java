package ua.in.leopard.androidCoocooAfisha;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class dataCollector {
	private final Context myContext;
	
	public dataCollector(Context context) {
		this.myContext = context;
	}
	
	public void getTheatersData(){
		this.getDataFromJSON(JsonClient.getData(EditPreferences.getTheaterUrl(this.myContext)));
	}
	
	public void getDataFromJSON(JSONObject js_obj){
		try {
			JSONArray nameArray=js_obj.names();
			JSONArray valArray = js_obj.toJSONArray(nameArray);
			Log.i("dataCollector","<jsonobject>\n"+valArray.toString()+"\n</jsonobject>");
			for(int i=0;i<valArray.length();i++){
                Log.i("dataCollector","<jsonname"+i+">\n"+nameArray.getString(i)+"\n</jsonname"+i+">\n"
                        +"<jsonvalue"+i+">\n"+valArray.getString(i)+"\n</jsonvalue"+i+">");
            }

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
