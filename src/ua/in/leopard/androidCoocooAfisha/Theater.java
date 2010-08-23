package ua.in.leopard.androidCoocooAfisha;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Theater extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.theater);
        
        Bundle extras = getIntent().getExtras();
        int theater_id = 0;
        if(extras != null) {
        	Log.i("Theater", "Theater: " + extras.get("theater_id"));
        	//theater_id = Integer.parseInt(extras.getString("theater_id"));
        }
        
        if (theater_id != 0){
        	DatabaseHelper DatabaseHelperObject = new DatabaseHelper(this);
        	TheaterDB theater_main = DatabaseHelperObject.getTheater(theater_id);
        	if (theater_main != null){
        		Log.i("Theater", "Theater: " + theater_main.getTitle());
        	}
        }
	}
}
