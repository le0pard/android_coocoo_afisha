package ua.in.leopard.androidCoocooAfisha;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class dataCollector {
	private static final String AFISHA_URL="http://bash.leopard.in.ua/json.json";
	
	public static void getAfishaData(){
		dataCollector.getDataFromJSON(JsonClient.getData(AFISHA_URL));
	}
	
	public static void getDataFromJSON(JSONObject js_obj){
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
