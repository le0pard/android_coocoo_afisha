package ua.in.leopard.androidCoocooAfisha;


import java.util.List;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.widget.RemoteViews;

public class AfishaWidgetProvider extends AppWidgetProvider {
	@Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] app_widget_ids) {
		
		DatabaseHelper DatabaseHelperObject = new DatabaseHelper(context);
	    List<CinemaDB> cinemas = DatabaseHelperObject.getTodayCinemas();
		
		final int count = app_widget_ids.length;
        for (int i=0; i< count; i++) {
            updateAppWidget(context, appWidgetManager, app_widget_ids[i], cinemas);
        }
	}
	
    @Override
    public void onEnabled(Context context) {
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(
                new ComponentName("ua.in.leopard.androidCoocooAfisha", ".AfishaWidgetBroadcastReceiver"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }
    @Override
    public void onDisabled(Context context) {
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(
                new ComponentName("ua.in.leopard.androidCoocooAfisha", ".AfishaWidgetBroadcastReceiver"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }
    
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
            int appWidgetId, List<CinemaDB> cinemas) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.afisha_widget_provider);
        
        views.setImageViewResource(R.id.cinema_poster, R.drawable.poster);
        
        if (cinemas.size() > 0){
        	Bitmap poster = cinemas.get(1).getPosterImg();
    		if (poster != null){
    			views.setImageViewBitmap(R.id.cinema_poster, poster);
    		}
        }
        
        //Intent form = new Intent(context, HelloAndroid.class);
        
        //PendingIntent main = PendingIntent.getActivity(context, 0, form, 0);
        //views.setOnClickPendingIntent(R.id.start_program, main);

        // Tell the widget manager
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
