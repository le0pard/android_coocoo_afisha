package ua.in.leopard.androidCoocooAfisha;

import ua.in.leopard.androidCoocooAfisha.provider.SearchPopcornProvider;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.SearchRecentSuggestions;
import android.util.AttributeSet;
import android.widget.Toast;

public class SuggestionsClearDialogPreference extends android.preference.DialogPreference {
	private final Context myContext;

	public SuggestionsClearDialogPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.myContext = context;
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which){
		if (DialogInterface.BUTTON_POSITIVE == which){
			SearchRecentSuggestions suggestions = new SearchRecentSuggestions(myContext,
					SearchPopcornProvider.AUTHORITY, SearchPopcornProvider.MODE);
			suggestions.clearHistory();
			Toast.makeText(this.myContext, this.myContext.getString(R.string.suggestions_clear_message), Toast.LENGTH_LONG).show();
		}
		
	}

}
