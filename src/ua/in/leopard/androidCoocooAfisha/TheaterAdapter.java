package ua.in.leopard.androidCoocooAfisha;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TheaterAdapter extends BaseAdapter {
	
	private Context context;
	private List<TheaterDB> theaters_list;
	private HashMap<Integer, TheaterAdapterView> listTheaterAdapterView = new HashMap<Integer, TheaterAdapterView>();
	
	public TheaterAdapter(Context context, List<TheaterDB> theaters_list){
		this.context = context;
		this.theaters_list = theaters_list;
	}
	
	@Override
	public int getCount() {
		return theaters_list.size();
	}

	@Override
	public Object getItem(int position) {
		return theaters_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TheaterDB entry = theaters_list.get(position);
		if (null == listTheaterAdapterView.get(position)){
			listTheaterAdapterView.put(position, new TheaterAdapterView(context, entry));
		}
		return listTheaterAdapterView.get(position);
	}

}
