package ua.in.leopard.androidCoocooAfisha;

import android.app.Activity;
import android.content.Intent;
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
