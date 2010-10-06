package ua.in.leopard.androidCoocooAfisha;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.widget.RemoteViews;

public class AfishaWidgetProvider extends AppWidgetProvider {
	@Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] app_widget_ids) {
		final int count = app_widget_ids.length;
        for (int i=0; i< count; i++) {
            updateAppWidget(context, appWidgetManager, app_widget_ids[i]);
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
            int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.afisha_widget_provider);
        
        //Intent form = new Intent(context, HelloAndroid.class);
        //PendingIntent main = PendingIntent.getActivity(context, 0, form, 0);
        //views.setOnClickPendingIntent(R.id.start_program, main);

        // Tell the widget manager
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
