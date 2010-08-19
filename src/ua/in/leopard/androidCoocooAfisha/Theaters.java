package ua.in.leopard.androidCoocooAfisha;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Theaters extends Activity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.theaters);
		
        View backButton = findViewById(R.id.home_button);
        backButton.setOnClickListener(this);
        
//        TableLayout TheatersTable = (TableLayout)findViewById(R.id.theaters_table);
        DatabaseHelper DatabaseHelperObject = new DatabaseHelper(this);
        Cursor result = DatabaseHelperObject.getTheaters();
        while (!result.isAfterLast()) {
/*
        	TableRow tr = new TableRow(this);
        	
        	LinearLayout lin_l = new LinearLayout(this);
        	lin_l.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    80));
        	lin_l.setOrientation(LinearLayout.VERTICAL);
        	lin_l.setPadding(10, 10, 10, 10);
        	
        	TextView tw = new TextView(this);
        	tw.setText(result.getString(result.getColumnIndex("title")));
        	tw.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
        	
        	TextView tw_address = new TextView(this);
        	tw_address.setText(result.getString(result.getColumnIndex("address")));
        	tw_address.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
        	
        	lin_l.addView(tw);
        	lin_l.addView(tw_address);
        	
        	tr.addView(lin_l);
        	
        	TheatersTable.addView(tr,new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
*/        	
        	result.moveToNext();
        }
        result.close();
        
	}

	@Override
	public void onClick(View v) {
		Intent i;
		switch (v.getId()) {
		  case R.id.home_button:
			 finish();
	         break; 
	      }
	}

}
