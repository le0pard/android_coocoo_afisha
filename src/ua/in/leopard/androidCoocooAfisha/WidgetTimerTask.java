package ua.in.leopard.androidCoocooAfisha;

import java.util.TimerTask;

import android.appwidget.AppWidgetManager;
import android.content.Context;

public class WidgetTimerTask extends TimerTask {
	private int app_widget_id;
	private Context context;
	private AppWidgetManager appWidgetManager;
	
	public WidgetTimerTask(Context context, AppWidgetManager appWidgetManager, int app_widget_id){
		this.context = context;
		this.appWidgetManager = appWidgetManager;
		this.app_widget_id = app_widget_id;
	}
	
	@Override
	public void run() {
		AfishaWidgetProvider.updatePoster(context, appWidgetManager, app_widget_id);
	}
	
}
