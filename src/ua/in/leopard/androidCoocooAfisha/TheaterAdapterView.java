package ua.in.leopard.androidCoocooAfisha;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TheaterAdapterView extends LinearLayout {

	public TheaterAdapterView(Context context, TheaterDB entry) {
		super(context);
		
		this.setOrientation(HORIZONTAL);
		this.setTag(entry);
		
		View v = inflate(context, R.layout.theater_row, null);
		
		TextView theaterTitle = (TextView)v.findViewById(R.id.theater_title);
		theaterTitle.setText(entry.getTitle());
		
		TextView theaterAddress = (TextView)v.findViewById(R.id.theater_address);
		theaterAddress.setText(Html.fromHtml(entry.getAddress()));
		
		addView(v);
	}

}
