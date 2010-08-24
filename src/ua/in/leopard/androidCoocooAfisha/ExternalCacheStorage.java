package ua.in.leopard.androidCoocooAfisha;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

public class ExternalCacheStorage {
	private Boolean extStorageWrittable = false;
	
	public ExternalCacheStorage(){
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
			this.extStorageWrittable = true;
		}
	}
	
	public void saveImg(Bitmap bitmap, String filename){
		if (this.extStorageWrittable){
			OutputStream fOut;
			try {
				String path = "";
				path = Environment.getExternalStorageDirectory().toString();
				path = path + "/Android/data/ua.in.leopard.androidCoocooAfisha/cache/";
				Log.i("ExternalCacheStorage", "Path: " + path);
				File file = new File(path, filename);
				fOut = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 95, fOut);
				fOut.flush();
	            fOut.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public Bitmap loadImg(String filename){
		Bitmap bitmap = null;
		return bitmap;
	}
}
