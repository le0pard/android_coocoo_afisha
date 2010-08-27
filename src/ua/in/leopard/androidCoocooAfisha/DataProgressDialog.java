package ua.in.leopard.androidCoocooAfisha;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

public class DataProgressDialog extends AsyncTask<Void, String, Void> {
	private final Context myContext;
	private ProgressDialog pd;
	
	private DataCollector dataCollectorObject;
	
	public DataProgressDialog(Context myContext) {
		this.myContext = myContext;
		this.pd = ProgressDialog.show(this.myContext, "Обновление данных", "Пожалуйста, подождите...", true, false);
		this.dataCollectorObject = new DataCollector(this.myContext);
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		publishProgress("Обновление кинотеатров...");
        dataCollectorObject.getTheatersData(); 
        
        publishProgress("Обновление фильмов...");
        dataCollectorObject.getCinemasData(); 
        
        publishProgress("Удаление старых данных...");
        dataCollectorObject.clearOldData();
/*        
        if (dataCollectorObject.getInetError()){
        	msg = handler.obtainMessage();
    		b = new Bundle(); 
            b.putInt("phase", 1); 
            b.putString("message", "Ошибка обновления.\nПроверьте настройки подключения к Интернету..."); 
            msg.setData(b);
            handler.sendMessage(msg);
            try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				
			}
        }
*/        
		return null;
	}
	
	@Override
	protected void onProgressUpdate(String... message) {
		if (pd != null){
			try {
				pd.setMessage(message[0]);
			} catch(Exception e){
				pd = null;
				if (!isCancelled()){
					cancel(true);
				}
			}
		}
	}
	
	@Override
	protected void onPostExecute(Void unused) {
		try {
			pd.dismiss();
		} catch(Exception e){
			pd = null;
		}
		if (pd != null && dataCollectorObject.getInetError()){
			new AlertDialog.Builder(this.myContext)
			.setTitle("Ошибка обновления")
			.setMessage("Проверьте настройки подключения к Интернету...")
			.setNeutralButton("Закрыть", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dlg, int sumthin) {
					// do nothing – it will close on its own
				}
			}).show();
		}
	}


}
