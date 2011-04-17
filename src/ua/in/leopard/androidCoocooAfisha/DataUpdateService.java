package ua.in.leopard.androidCoocooAfisha;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class DataUpdateService extends Service {
	private Timer timer = new Timer();
	private int hours_interval = 1;
	public static final int STATUS_STARTED = 1;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		Toast.makeText(this, getString(R.string.autoupdate_service_start), Toast.LENGTH_LONG).show();
		this.hours_interval = Integer.parseInt(EditPreferences.getAutoUpdateTime(this));
		if (0 != Integer.parseInt(EditPreferences.getAutoUpdateTime(this))){
			startTimer();
		}
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, getString(R.string.autoupdate_service_stop), Toast.LENGTH_LONG).show();
		if (timer != null){
			timer.cancel();
		} 
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		//do nothing
	}
	
	private void startTimer(){
		int timeout = 1000 * 3600 * this.hours_interval;
		timer.scheduleAtFixedRate(
			new TimerTask() {
				public void run() {
					updateAppData();
				}
			}, 
			timeout, timeout);
	}
	
	private void updateAppData(){
		DataCollector dataCollectorObject = new DataCollector(this);
		dataCollectorObject.getTheatersData();
		dataCollectorObject.getCinemasData();
		dataCollectorObject.clearOldData();
	}

}
