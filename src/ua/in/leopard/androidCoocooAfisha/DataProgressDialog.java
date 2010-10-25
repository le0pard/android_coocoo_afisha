package ua.in.leopard.androidCoocooAfisha;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

public class DataProgressDialog extends AsyncTask<Void, String, Void> {
	private Context myContext;
	private ProgressDialog pd;
	private DataCollector dataCollectorObject;
	
	
	public DataProgressDialog(Context myContext) {
		this.myContext = myContext;
	}
	
	public void newView(Context myContext){
		this.myContext = myContext;
		this.pd = ProgressDialog.show(this.myContext, this.myContext.getString(R.string.dialog_title), this.myContext.getString(R.string.dialog_message), true, false);
	}
	
	public void closeView(){
		this.pd.dismiss();
	}
	
	@Override
    protected void onPreExecute() {
		this.pd = ProgressDialog.show(this.myContext, this.myContext.getString(R.string.dialog_title), this.myContext.getString(R.string.dialog_message), true, false);
		this.dataCollectorObject = new DataCollector(this.myContext);
    }
	
	@Override
	protected Void doInBackground(Void... params) {
		publishProgress(this.myContext.getString(R.string.dialog_message_theaters));
        dataCollectorObject.getTheatersData(); 
        
        publishProgress(this.myContext.getString(R.string.dialog_message_cinemas));
        dataCollectorObject.getCinemasData(); 
        
        publishProgress(this.myContext.getString(R.string.dialog_message_cleardb));
        dataCollectorObject.clearOldData();
     
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
			.setTitle(this.myContext.getString(R.string.update_error_title))
			.setMessage(this.myContext.getString(R.string.update_error_message))
			.setNeutralButton(this.myContext.getString(R.string.update_error_button), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dlg, int sumthin) {
					// do nothing – it will close on its own
				}
			}).show();
		}
	}


}
