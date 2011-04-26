package ua.in.leopard.androidCoocooAfisha;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CinemaAdapter extends BaseAdapter {
	
	private Context context;
	private List<CinemaDB> cinema_list;
	private HashMap<Integer, CinemaAdapterView> listCinemaAdapterView = new HashMap<Integer, CinemaAdapterView>();
	
	public CinemaAdapter(Context context, List<CinemaDB> cinema_list){
		this.context = context;
		this.cinema_list = cinema_list;
	}
	
	@Override
	public int getCount() {
		return cinema_list.size();
	}

	@Override
	public Object getItem(int position) {
		return cinema_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CinemaDB entry = cinema_list.get(position);
		if (null == listCinemaAdapterView.get(position)){
			listCinemaAdapterView.put(position, new CinemaAdapterView(context, entry));
		}
		return listCinemaAdapterView.get(position);
	}

}
