package ua.in.leopard.androidCoocooAfisha;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class DataProgressDialog implements Runnable {
	private final Context myContext;
	private ProgressDialog pd;
	private DataCollector dataCollectorObject;
	
	public DataProgressDialog(Context myContext) {
		this.myContext = myContext;
		pd = ProgressDialog.show(this.myContext, "Обновление данных", "Пожалуйста, подождите...", true, false);
		Thread thread = new Thread(this);
		thread.start();
	}

	public DataProgressDialog(Context myContext, String title, String message) {
		this.myContext = myContext;
		pd = ProgressDialog.show(this.myContext, title, message, true, false);
		Thread thread = new Thread(this);
		thread.start();
	}

	public void run() {
		dataCollectorObject = new DataCollector(this.myContext);
        dataCollectorObject.getTheatersData();
		handler.sendEmptyMessage(0);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			pd.dismiss();
			//
		}
	};


}
