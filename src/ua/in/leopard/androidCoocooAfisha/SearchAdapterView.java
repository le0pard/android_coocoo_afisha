package ua.in.leopard.androidCoocooAfisha;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchAdapterView extends LinearLayout {
	
	public SearchAdapterView(Context context, SearchResDB entry) {
		super(context);
		
		this.setOrientation(HORIZONTAL);
		this.setTag(entry);
		
		View v = inflate(context, R.layout.cinema_row, null);
		
		TextView theaterTitle = (TextView)v.findViewById(R.id.cinema_title);
		theaterTitle.setText(entry.getTitle());
		
		TextView theaterAddress = (TextView)v.findViewById(R.id.cinema_orig_title);
		theaterAddress.setText(Html.fromHtml(entry.getOrigTitle()));
		
		addView(v);
	}
}
