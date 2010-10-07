package ua.in.leopard.androidCoocooAfisha;

import java.util.List;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class AfishaWidgetBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//String action = intent.getAction();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisWidget = new ComponentName(context, AfishaWidgetProvider.class);
        int[] app_widget_ids = appWidgetManager.getAppWidgetIds(thisWidget);
        
        DatabaseHelper DatabaseHelperObject = new DatabaseHelper(context);
	    List<CinemaDB> cinemas = DatabaseHelperObject.getTodayCinemas();

        final int count = app_widget_ids.length;
        for (int i=0; i< count; i++) {
        	AfishaWidgetProvider.updateAppWidget(context, appWidgetManager, app_widget_ids[i], cinemas);
        }
		
	}

}
