package ua.in.leopard.androidCoocooAfisha;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class DataProgressDialog implements Runnable {
	private final Context myContext;
	private ProgressDialog pd;
	private Message msg;
	
	private DataCollector dataCollectorObject;
	
	public DataProgressDialog(Context myContext) {
		this.myContext = myContext;
		pd = ProgressDialog.show(this.myContext, "Обновление данных", "Пожалуйста, подождите...", true, false);

		Thread thread = new Thread(this);
		thread.start();
	}

	public void run() {
		dataCollectorObject = new DataCollector(this.myContext);
		
		msg = handler.obtainMessage();
		Bundle b = new Bundle(); 
        b.putInt("phase", 1); 
        b.putString("message", "Обновление кинотеатров..."); 
        msg.setData(b);
        handler.sendMessage(msg);
        
        dataCollectorObject.getTheatersData(); 
        handler.sendEmptyMessage(0);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int phase = msg.getData().getInt("phase"); 
			String message = msg.getData().getString("message");
			
			switch(phase) { 
                 case 1: 
                	  pd.setMessage(message);
                      break; 
                 default:
                	  pd.dismiss();
                      break; 
            }
		}
	};


}
