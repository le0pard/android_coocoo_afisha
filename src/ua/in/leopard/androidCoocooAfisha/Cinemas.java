package ua.in.leopard.androidCoocooAfisha;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class Cinemas extends Activity implements OnClickListener {
	private CinemaAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.cinemas);
        
        ListView CinemasList = (ListView)findViewById(R.id.cinemas_list);
        DatabaseHelper DatabaseHelperObject = new DatabaseHelper(this);
        List<CinemaDB> cinemas = DatabaseHelperObject.getTodayCinemas();
        adapter = new CinemaAdapter(this, cinemas);
        CinemasList.setAdapter(adapter);
        //TheaterList.setOnItemClickListener(new TheaterAdapterClick());
	}

	@Override
	public void onClick(View v) {

	}

}
