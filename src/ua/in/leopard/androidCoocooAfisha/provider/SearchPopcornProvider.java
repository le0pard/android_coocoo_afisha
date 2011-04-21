package ua.in.leopard.androidCoocooAfisha.provider;

import android.content.SearchRecentSuggestionsProvider;

public class SearchPopcornProvider extends SearchRecentSuggestionsProvider{
	public static final String AUTHORITY = "ua.in.leopard.androidCoocooAfisha.provider.SearchPopcornProvider";
	public static final int MODE = DATABASE_MODE_QUERIES;
	
	public SearchPopcornProvider() {
        super();
        setupSuggestions(AUTHORITY, MODE);
    }
}
