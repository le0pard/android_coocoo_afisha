package ua.in.leopard.androidCoocooAfisha;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public abstract class MainActivity extends Activity {
	private String GOOGLE_ANALITYC_CODE = "UA-7068020-8";
	private int GOOGLE_ANALITYC_TIMEOUT = 300;
	public GoogleAnalyticsTracker tracker;

	/**
	 * onCreate - called when the activity is first created.
	 *
	 * Called when the activity is first created. 
	 * This is where you should do all of your normal static set up: create views, bind data to lists, etc. 
	 * This method also provides you with a Bundle containing the activity's previously frozen state, if there was one.
	 * 
	 * Always followed by onStart().
	 *
	 */

	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    // Start the tracker in manual dispatch mode...
	    tracker = GoogleAnalyticsTracker.getInstance();
	    tracker.start(GOOGLE_ANALITYC_CODE, GOOGLE_ANALITYC_TIMEOUT, this);
	}
	
	
	/**
	 */
	// Click Methods

	/**
	 * Handle the click on the home button.
	 * 
	 * @param v View
	 * @return void
	 */

	public void onClickHome(View v){
	    goHome(this);
	}
	
	public void goHome(Context context) {
	    final Intent intent = new Intent(context, androidCoocooAfisha.class);
	    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    context.startActivity(intent);
	}
	
	/**
	 * Use the activity label to set the text in the activity's title text view.
	 * The argument gives the name of the view.
	 *
	 * <p> This method is needed because we have a custom title bar rather than the default Android title bar.
	 * See the theme definitons in styles.xml.
	 * 
	 * @param textViewId int
	 * @return void
	 */

	public void setTitle(CharSequence title){
		super.setTitle(title);
		setTitleFromActivityLabel(title);
	}
	
	public void setTitleFromActivityLabel(CharSequence title){
	    TextView main_title = (TextView)findViewById(R.id.main_title);
	    if (main_title != null) main_title.setText(Html.fromHtml(title.toString()));
	}

	public void onClickSearch(View v){
		onSearchRequested();
	}
	
	
	/* check if is online */
	public Boolean isOnline(){
		ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if(null == info || !info.isConnected()){
			return false;
		}		
		return true;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	    // Stop the tracker when it is no longer needed.
	    tracker.stop();
	}
}
