package ua.in.leopard.androidCoocooAfisha;

import java.util.HashMap;
import java.util.Timer;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.RemoteViews;
import android.widget.Spinner;

public class AfishaWidgetConfigure extends Activity implements OnClickListener, OnItemSelectedListener {
	private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	
	public AfishaWidgetConfigure() {
        super();
    }
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setResult(RESULT_CANCELED);
      setContentView(R.layout.afisha_widget_configure);
      
      // Find the widget id from the intent. 
      Intent intent = getIntent();
      Bundle extras = intent.getExtras();
      if (extras != null) {
          mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
      }

      // If they gave us an intent without the widget id, just bail.
      if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
          finish();
      }
      
      Spinner spiner = (Spinner)findViewById(R.id.time_spinner);
      spiner.setOnItemSelectedListener(this);
      ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.widget_time_title, android.R.layout.simple_spinner_item);
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      spiner.setAdapter(adapter);
      
      View okButton = findViewById(R.id.widget_ok_button);
      okButton.setOnClickListener(this);
      View cancelButton = findViewById(R.id.widget_cancel_button);
      cancelButton.setOnClickListener(this);
      
    }
	
	public void stopWidgetTimer(int id){
		Timer timer = SingletoneStorage.get_value_timers(id);
		if (timer != null){
			timer.cancel();
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
		int interval = (position + 1) * 10;
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}

	@Override
	public void onClick(View v) {
		Intent resultValue = new Intent();
		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
		switch (v.getId()) {
		  case R.id.widget_ok_button:
			  	final Context context = AfishaWidgetConfigure.this;
	            // When the button is clicked, save the string in our prefs and return that they
	            // clicked OK.
	            //String titlePrefix = mAppWidgetPrefix.getText().toString();
	            //saveTitlePref(context, mAppWidgetId, titlePrefix);
	            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
	            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.afisha_widget_provider);
	            appWidgetManager.updateAppWidget(mAppWidgetId, views);
	            
	            setResult(RESULT_OK, resultValue);
	            finish();
	         break;
		  case R.id.widget_cancel_button:
			  setResult(RESULT_CANCELED, resultValue);
			  stopWidgetTimer(mAppWidgetId);
			  finish();
			  break;      
	      }
	}
}
