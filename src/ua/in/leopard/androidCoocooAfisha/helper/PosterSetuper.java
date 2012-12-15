package ua.in.leopard.androidCoocooAfisha.helper;

import java.lang.ref.WeakReference;

import ua.in.leopard.androidCoocooAfisha.CinemaDB;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

public class PosterSetuper extends AsyncTask<Void, String, Void> {
	//private Context myContext;
	private CinemaDB entry;
	private final WeakReference<ImageView> image;
	private Bitmap imageBitmap;
	
	public PosterSetuper(Context context, CinemaDB entry, ImageView image){
		//this.myContext = context;
		this.entry = entry;
		this.image = new WeakReference<ImageView>(image);
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		this.imageBitmap = entry.getCachedImg();
		return null;
	}
	
	@Override
    protected void onPostExecute(Void unused) {
		if (this.image != null && this.imageBitmap != null) {
			ImageView imageView = this.image.get();
			imageView.setImageBitmap(this.imageBitmap);
		}
	}


}
