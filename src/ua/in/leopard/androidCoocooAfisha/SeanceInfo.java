package ua.in.leopard.androidCoocooAfisha;

import android.content.Intent;
import android.os.Bundle;
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
        	cinema_main = DatabaseHelperObject.getCinema(cinema_id);
        	theater_main = DatabaseHelperObject.getTheater(theater_id);
        	if (null != cinema_main && null != theater_main){
        		setTitle(cinema_main.getTitle());
        		initTwoButtonsBar();
        		
        	}
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
					button_one_text.setText(R.string.theater_call_phone);
				}
				ImageView button_img = (ImageView)two_buttons_bar_button_one.findViewById(R.id.two_buttons_button_image);
				button_img.setImageResource(R.drawable.booking_button);
			}
			View two_buttons_bar_button_second = two_buttons_bar.findViewById(R.id.two_buttons_bar_button_second);
			if (two_buttons_bar_button_second != null){
				two_buttons_bar_button_second.setOnClickListener(this);
				TextView button_second_text = (TextView)two_buttons_bar_button_second.findViewById(R.id.two_buttons_button_label);
				if (button_second_text != null){
					button_second_text.setText(R.string.theater_map_location);
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
