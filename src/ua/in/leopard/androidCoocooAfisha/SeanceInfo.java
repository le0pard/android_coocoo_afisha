package ua.in.leopard.androidCoocooAfisha;

import ua.in.leopard.androidCoocooAfisha.helper.ImageDownloader;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class SeanceInfo extends MainActivity implements OnClickListener {
	private TheaterDB theater_main;
	private CinemaDB cinema_main;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.seance_info);
      
      Bundle extras = getIntent().getExtras();
      int cinema_id = 0;
      int theater_id = 0;
      if(extras != null) {
        cinema_id = extras.getInt("cinema_id", 0);
        theater_id = extras.getInt("theater_id", 0);
      }
      
      
      if (cinema_id != 0 && theater_id != 0){
        	DatabaseHelper DatabaseHelperObject = new DatabaseHelper(this);
        	theater_main = DatabaseHelperObject.getTheater(theater_id);
        	if (null != theater_main){
	        	cinema_main = DatabaseHelperObject.getCinemaWithAfisha(theater_main, cinema_id, extras.getBoolean("is_today", true));
	        	if (null != cinema_main){
	        		setTitle(cinema_main.getTitle());
	        		
	        		setCinemaInfo();
	        		setTheaterInfo();
	        		initTwoButtonsBar();
	        	}
        	}
      }
	}
	
	private void setTheaterInfo(){
		TextView theater_name = (TextView)findViewById(R.id.theater_name);
		theater_name.setText(theater_main.getTitle());
		
		TextView zalTitle = (TextView)findViewById(R.id.cinema_zal_title);
		if (cinema_main.getZalTitle() != null && cinema_main.getZalTitle().length() > 0){
			zalTitle.setText(Html.fromHtml(cinema_main.getZalTitle()));
		} else {
			zalTitle.setText(R.string.not_set);
		}
		
		TextView cinemaTimes = (TextView)findViewById(R.id.cinema_times);
		if (cinema_main.getTimes() != null && cinema_main.getTimes().length() > 0){
			String cinema_times = cinema_main.getTimes();
			cinema_times = cinema_times.replaceAll("(?i)([01]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9]);", "$1:$2;");
			cinemaTimes.setText(cinema_times);
		} else {
			cinemaTimes.setText(R.string.not_set);
		}
		
		TextView cinemaPrices = (TextView)findViewById(R.id.cinema_prices);
		if (cinema_main.getPrices() != null && cinema_main.getPrices().length() > 0){
			cinemaPrices.setText(cinema_main.getPrices());
		} else {
			cinemaPrices.setText(R.string.not_set);
		}
	}
	
	private void setCinemaInfo(){
		ImageView cinemaPoster = (ImageView)findViewById(R.id.cinema_poster);
		if (EditPreferences.isNoPosters(this)){
			cinemaPoster.setImageResource(R.drawable.no_poster);
		} else {
			if (null == cinema_main.getCachedImg()){
				ImageDownloader imageDownloader = new ImageDownloader(this);
    			imageDownloader.download(cinema_main.getPosterUrl(), cinemaPoster);
			} else {
				cinemaPoster.setImageBitmap(cinema_main.getCachedImg());
			}
			
		}
		
		TextView cinema_orig_title = (TextView)findViewById(R.id.cinema_orig_title);
		if (cinema_main.getOrigTitle() != null && cinema_main.getOrigTitle().length() > 0){
			cinema_orig_title.setText(Html.fromHtml(cinema_main.getOrigTitle()));
		} else {
			cinema_orig_title.setText(R.string.not_set);
		}
		
		TextView cinema_year = (TextView)findViewById(R.id.cinema_year);
		if (cinema_main.getYear() != null && Integer.parseInt(cinema_main.getYear()) > 0){
			cinema_year.setText(cinema_main.getYear());
		} else {
			cinema_year.setText(R.string.not_set);
		}
		
		TextView cinema_description = (TextView)findViewById(R.id.cinema_description);
		if (cinema_main.getDescription() != null && cinema_main.getDescription().length() > 0){
			cinema_description.setText(Html.fromHtml(cinema_main.getDescription()));
		} else {
			cinema_description.setText(R.string.not_set);
		}
	    
	    String cinema_casts_data = cinema_main.getCasts();
	    TextView cinema_casts = (TextView)findViewById(R.id.cinema_casts);
	    if (cinema_casts_data != null && cinema_casts_data.length() > 0){
	    	cinema_casts.setText(cinema_casts_data);
	    } else {
	    	cinema_casts.setText(R.string.not_set);
	    }
	}
	
	private void initTwoButtonsBar(){
		View two_buttons_bar = findViewById(R.id.two_buttons_bar);
		if (two_buttons_bar != null){
			View two_buttons_bar_button_one = two_buttons_bar.findViewById(R.id.two_buttons_bar_button_one);
			if (two_buttons_bar_button_one != null){
				two_buttons_bar_button_one.setOnClickListener(this);
				TextView button_one_text = (TextView)two_buttons_bar_button_one.findViewById(R.id.two_buttons_button_label);
				if (button_one_text != null){
					button_one_text.setText(R.string.seance_theater);
				}
				ImageView button_img = (ImageView)two_buttons_bar_button_one.findViewById(R.id.two_buttons_button_image);
				button_img.setImageResource(R.drawable.booking_button);
			}
			View two_buttons_bar_button_second = two_buttons_bar.findViewById(R.id.two_buttons_bar_button_second);
			if (two_buttons_bar_button_second != null){
				two_buttons_bar_button_second.setOnClickListener(this);
				TextView button_second_text = (TextView)two_buttons_bar_button_second.findViewById(R.id.two_buttons_button_label);
				if (button_second_text != null){
					button_second_text.setText(R.string.seance_cinema);
				}
				ImageView button_img = (ImageView)two_buttons_bar_button_second.findViewById(R.id.two_buttons_button_image);
				button_img.setImageResource(R.drawable.map_button);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		  case R.id.two_buttons_bar_button_one:
			Intent intent_one = new Intent(this, Theater.class);
			Bundle bundle_one = new Bundle();
			bundle_one.putInt("theater_id", this.theater_main.getId());
			intent_one.putExtras(bundle_one);
			startActivity(intent_one);
			break;
		  case R.id.two_buttons_bar_button_second:
			Intent intent_second = new Intent(this, Cinema.class);
			Bundle bundle_second = new Bundle();
			bundle_second.putInt("cinema_id", this.cinema_main.getId());
			intent_second.putExtras(bundle_second);
			startActivity(intent_second);
	        break;
	    }
	}
}
