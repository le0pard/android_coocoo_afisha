package ua.in.leopard.androidCoocooAfisha;

import java.util.List;

import com.google.analytics.tracking.android.EasyTracker;

import ua.in.leopard.androidCoocooAfisha.helper.ImageDownloader;
import ua.in.leopard.androidCoocooAfisha.helper.PosterSetuper;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Cinema extends MainActivity implements OnClickListener, OnItemClickListener {
	private final String AFISHA_TODAY_TAB = "afisha_today_tag";
	private final String AFISHA_TOMORROW_TAB = "afisha_tomorrow_tag";
	private View tab_change_button;
	private TabHost tabs;
	private TheaterAdapter adapter_today, adapter_tomorrow;
	private CinemaDB cinema_main;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.cinema);
        
        initTabHost();
        initTwoButtonsBar();
        
        Bundle extras = getIntent().getExtras();
        int cinema_id = 0;
        if(extras != null) {
        	cinema_id = extras.getInt("cinema_id", 0);
        }
        
        if (cinema_id != 0){
        	DatabaseHelper DatabaseHelperObject = new DatabaseHelper(this);
        	cinema_main = DatabaseHelperObject.getCinema(cinema_id);
        	if (cinema_main != null){
        		setTitle(cinema_main.getTitle());
        		TextView cinema_title = (TextView)findViewById(R.id.cinema_title);
        		if (cinema_title != null){
        			cinema_title.setText(Html.fromHtml(cinema_main.getTitle()));
        		}
        		
        		ImageView cinemaPoster = (ImageView)findViewById(R.id.cinema_poster);
        		if (EditPreferences.isNoPosters(this)){
        			cinemaPoster.setImageResource(R.drawable.no_poster);
        		} else {
        			if (cinema_main.isHavePoster()){
        				//cinemaPoster.setImageBitmap(cinema_main.getCachedImg());
        				PosterSetuper posterSetuper = new PosterSetuper(this);
        				posterSetuper.setImage(cinema_main, cinemaPoster);
        			} else if (!EditPreferences.isCachedPosters(this)) {
        				ImageDownloader imageDownloader = new ImageDownloader(this);
        				imageDownloader.download(cinema_main.getPosterUrl(), cinemaPoster);
        			} else {
        				cinemaPoster.setImageResource(R.drawable.poster);
        			}
        		}
        		
        		TextView cinema_orig_title = (TextView)findViewById(R.id.cinema_orig_title);
        		if (cinema_orig_title != null){
        			if (cinema_main.getOrigTitle() != null && cinema_main.getOrigTitle().length() > 0){
        				cinema_orig_title.setText(Html.fromHtml(cinema_main.getOrigTitle()));
        			} else {
        				cinema_orig_title.setText(R.string.not_set);
        			}
        		}
        		
        		TextView cinema_year = (TextView)findViewById(R.id.cinema_year);
    			if (cinema_year != null){
    				if (cinema_main.getYear() != null && Integer.parseInt(cinema_main.getYear()) != 0){
    					cinema_year.setText(cinema_main.getYear());
    				} else {
    					cinema_year.setText(R.string.not_set);
    				}
    			}
        		
        		ListView afishaTodayList = (ListView)findViewById(R.id.afisha_today_list);
                List<TheaterDB> theaters_today = DatabaseHelperObject.getTodayOrTomorrowByCinema(cinema_main, true);
                adapter_today = new TheaterAdapter(this, theaters_today);
                afishaTodayList.setAdapter(adapter_today);
                afishaTodayList.setOnItemClickListener(this);
                
                ListView afishaTomorrowList = (ListView)findViewById(R.id.afisha_tomorrow_list);
                List<TheaterDB> theaters_tomorrow = DatabaseHelperObject.getTodayOrTomorrowByCinema(cinema_main, false);
                adapter_tomorrow = new TheaterAdapter(this, theaters_tomorrow);
                afishaTomorrowList.setAdapter(adapter_tomorrow);
                afishaTomorrowList.setOnItemClickListener(this);
        	}
        }

	}
	
	private void initTabHost(){
		tabs = (TabHost)findViewById(R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec(AFISHA_TODAY_TAB);
        spec.setContent(R.id.afisha_today_list);
        spec.setIndicator(getString(R.string.afisha_today));
        tabs.addTab(spec);
        spec = tabs.newTabSpec(AFISHA_TOMORROW_TAB);
        spec.setContent(R.id.afisha_tomorrow_list);
        spec.setIndicator(getString(R.string.afisha_tomorrow));
        tabs.addTab(spec);
        tabs.setCurrentTab(0);
	}
	
	private void initTwoButtonsBar(){
		View two_buttons_bar = findViewById(R.id.two_buttons_bar);
		if (two_buttons_bar != null){
			tab_change_button = two_buttons_bar.findViewById(R.id.two_buttons_bar_button_one);
			tabButtonChanged();
			
			View two_buttons_bar_button_second = two_buttons_bar.findViewById(R.id.two_buttons_bar_button_second);
			if (two_buttons_bar_button_second != null){
				two_buttons_bar_button_second.setOnClickListener(this);
				TextView button_second_text = (TextView)two_buttons_bar_button_second.findViewById(R.id.two_buttons_button_label);
				if (button_second_text != null){
					button_second_text.setText(R.string.more_info_text);
				}
				ImageView button_img = (ImageView)two_buttons_bar_button_second.findViewById(R.id.two_buttons_button_image);
				button_img.setImageResource(R.drawable.info_button);
			}
		}
	}
	
	private void tabButtonChanged(){
		if (tab_change_button != null){
			tab_change_button.setOnClickListener(this);
			ImageView button_img = (ImageView)tab_change_button.findViewById(R.id.two_buttons_button_image);
			TextView button_text = (TextView)tab_change_button.findViewById(R.id.two_buttons_button_label);
			if (AFISHA_TODAY_TAB == tabs.getCurrentTabTag()){
				if (button_text != null){
					button_text.setText(R.string.afisha_today);
				}
				button_img.setImageResource(R.drawable.today_button);
			} else {
				if (button_text != null){
					button_text.setText(R.string.afisha_tomorrow);
				}
				button_img.setImageResource(R.drawable.tomorrow_button);
			}
			
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		TheaterAdapter s_adapter = (TheaterAdapter)parent.getAdapter();
		TheaterDB theater_obj = (TheaterDB)s_adapter.getItem(position);
		//work
		Intent intent = new Intent(this, Theater.class);
		Bundle bundle = new Bundle();
		bundle.putInt("theater_id", theater_obj.getId());
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		  case R.id.two_buttons_bar_button_one:
			if (AFISHA_TODAY_TAB == tabs.getCurrentTabTag()){
				tabs.setCurrentTabByTag(AFISHA_TOMORROW_TAB);
			} else {
				tabs.setCurrentTabByTag(AFISHA_TODAY_TAB);
			}
			tabButtonChanged();
			break;
		  case R.id.two_buttons_bar_button_second:
			Intent intent = new Intent(this, CinemaInfo.class);
			Bundle bundle = new Bundle();
			bundle.putInt("cinema_id", this.cinema_main.getId());
			intent.putExtras(bundle);
			startActivity(intent);
	        break;
	    }		
	}
	
	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance().activityStart(this); // Add this method.
	}
	  
	@Override
	public void onStop() {
		super.onStop();
	    EasyTracker.getInstance().activityStop(this); // Add this method.
	}

}
