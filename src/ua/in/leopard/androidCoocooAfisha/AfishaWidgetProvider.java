package ua.in.leopard.androidCoocooAfisha;


import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;

public class AfishaWidgetProvider extends AppWidgetProvider {
	public static String FORCE_WIDGET_UPDATE = "ua.in.leopard.androidCoocooAfisha.FORCE_WIDGET_UPDATE";
	private Context myContext = null;
	private AppWidgetManager myAppWidgetManager = null;
	
	public class WidgetTimerTask extends TimerTask{
		private int id;
		
		public WidgetTimerTask(int id){
			this.id = id;
		}
		
		@Override
		public void run() {
			updatePoster(this.id);
		}
		
	}
	
	
	
	@Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] app_widget_ids) {
		this.myContext = context;
		this.myAppWidgetManager = appWidgetManager;
		
		updateAllWidgets(context, appWidgetManager, app_widget_ids);
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		if (FORCE_WIDGET_UPDATE.equals(intent.getAction()) || AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(intent.getAction())) {
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
	        ComponentName thisWidget = new ComponentName(context, AfishaWidgetProvider.class);
	        int[] app_widget_ids = appWidgetManager.getAppWidgetIds(thisWidget);
	        
			updateAllWidgets(context, appWidgetManager, app_widget_ids);
		}
		
	}
	
    @Override
    public void onEnabled(Context context) {
    	/*
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(
                new ComponentName("ua.in.leopard.androidCoocooAfisha", ".AfishaWidgetBroadcastReceiver"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        */
    }
    @Override
    public void onDisabled(Context context) {
    	/*
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(
                new ComponentName("ua.in.leopard.androidCoocooAfisha", ".AfishaWidgetBroadcastReceiver"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        */
    }
    @Override
    public void onDeleted(Context context, int[] app_widget_ids) {
    	super.onDeleted(context, app_widget_ids);
    	HashMap<Integer, Timer> timers = SingletoneStorage.get_timers();
    	for (int i=0; i< app_widget_ids.length; i++) {
    		Timer timer = timers.get(app_widget_ids[i]);
    		if (timer != null){
    			timer.cancel();
    		}
        }
    }
    
	private void updateAllWidgets(Context context, AppWidgetManager appWidgetManager, int[] app_widget_ids){
		DatabaseHelper DatabaseHelperObject = new DatabaseHelper(context);
		SingletoneStorage.set_cinemas(DatabaseHelperObject.getTodayCinemasForWidget());
		
        final int count = app_widget_ids.length;
        for (int i=0; i< count; i++) {
            updateAppWidget(context, appWidgetManager, app_widget_ids[i]);
        }
	}
    
    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
            int app_widget_id) {
    	
    	if (SingletoneStorage.get_cinemas().size() == 0){
    		RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.afisha_widget_provider);
    		view.setImageViewResource(R.id.cinema_poster, R.drawable.poster);
    		appWidgetManager.updateAppWidget(app_widget_id, view);
    	} else {
    		startTimer(app_widget_id);
    	}
    }
    
    public void startTimer(int app_widget_id){
    	HashMap<Integer, Timer> timers = SingletoneStorage.get_timers();
    	SingletoneStorage.put_cinemas_iterators(app_widget_id, 0);
    	
    	Timer old_timer = timers.get(app_widget_id);
		if (old_timer != null){
			old_timer.cancel();
		}
		
		Timer timer = new Timer();
        timers.put(app_widget_id, timer);
        timer.scheduleAtFixedRate(
        		new WidgetTimerTask(app_widget_id), 
        		0, 1000 * 5);
        
        SingletoneStorage.set_timers(timers);
    }
    
    private void updatePoster(int id){
    	
    	List<CinemaDB> cinemas_list = SingletoneStorage.get_cinemas();
    	int cinemas_iterator = SingletoneStorage.get_value_cinemas_iterators(id);
    	CinemaDB cinema_object = cinemas_list.get(cinemas_iterator);
    	
    	RemoteViews view = new RemoteViews(this.myContext.getPackageName(), R.layout.afisha_widget_provider);
    	if (cinemas_list.size() <= cinemas_iterator){
    		cinemas_iterator = 0;
		}
    	
    	Bitmap poster = cinema_object.getPosterImg();
		if (poster != null){
			view.setImageViewBitmap(R.id.cinema_poster, poster);
			// click
	        Intent form = new Intent(this.myContext, Cinema.class);
	        Bundle bundle = new Bundle();
			bundle.putInt("cinema_id", cinema_object.getId());
			form.putExtras(bundle);
	        PendingIntent main = PendingIntent.getActivity(this.myContext, 0, form, PendingIntent.FLAG_UPDATE_CURRENT);
	        view.setOnClickPendingIntent(R.id.cinema_widget_box, main);
		}
		
		cinemas_iterator++;
		if (cinemas_list.size() <= cinemas_iterator){
			cinemas_iterator = 0;
		}
		SingletoneStorage.put_cinemas_iterators(id, cinemas_iterator);

		myAppWidgetManager.updateAppWidget(id, view);
    }
}
