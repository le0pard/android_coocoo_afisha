package ua.in.leopard.androidCoocooAfisha;

import ua.in.leopard.androidCoocooAfisha.helper.ImageDownloader;
import ua.in.leopard.androidCoocooAfisha.helper.PosterSetuper;
import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("ViewConstructor")
public class SeanceAdapterView extends LinearLayout {

	public SeanceAdapterView(Context context, CinemaDB entry, ImageDownloader imageDownloader) {
		super(context);
		
		this.setOrientation(VERTICAL);
		this.setTag(entry);
		
		View v = inflate(context, R.layout.seance_row, null);
		
		ImageView cinemaPoster = (ImageView)v.findViewById(R.id.cinema_poster);
		if (EditPreferences.isNoPosters(context)){
			cinemaPoster.setImageResource(R.drawable.no_poster);
		} else {
			if (entry.isHavePoster()){
				//cinemaPoster.setImageBitmap(entry.getCachedImg());
				PosterSetuper backgroudTask = new PosterSetuper(context, entry, cinemaPoster);
		    	backgroudTask.execute();
			} else if (!EditPreferences.isCachedPosters(context)) {
				imageDownloader.download(entry.getPosterUrl(), cinemaPoster);
			} else {
				cinemaPoster.setImageResource(R.drawable.poster);
			}
		}
		
		TextView cinemaTitle = (TextView)v.findViewById(R.id.cinema_title);
		cinemaTitle.setText(Html.fromHtml(entry.getTitle()));
		
		TextView origTitle = (TextView)v.findViewById(R.id.cinema_orig_title);
		if (entry.getOrigTitle() != null && entry.getOrigTitle().length() > 0){
			origTitle.setText(Html.fromHtml(entry.getOrigTitle()));
		} else {
			origTitle.setText(R.string.not_set);
		}
		
		TextView zalTitle = (TextView)v.findViewById(R.id.cinema_zal_title);
		if (entry.getZalTitle() != null && entry.getZalTitle().length() > 0){
			zalTitle.setText(Html.fromHtml(entry.getZalTitle()));
		} else {
			zalTitle.setText(R.string.not_set);
		}
		
		TextView cinemaTimes = (TextView)v.findViewById(R.id.cinema_times);
		if (entry.getTimes() != null && entry.getTimes().length() > 0){
			String cinema_times = entry.getTimes();
			cinema_times = cinema_times.replaceAll("(?i)([01]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9]);", "$1:$2;");
			cinemaTimes.setText(cinema_times);
		} else {
			cinemaTimes.setText(R.string.not_set);
		}
		
		TextView cinemaPrices = (TextView)v.findViewById(R.id.cinema_prices);
		if (entry.getPrices() != null && entry.getPrices().length() > 0){
			cinemaPrices.setText(entry.getPrices());
		} else {
			cinemaPrices.setText(R.string.not_set);
		}
		
		
		addView(v);
	}

}
