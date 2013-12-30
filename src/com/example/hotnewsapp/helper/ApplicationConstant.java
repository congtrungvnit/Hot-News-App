package com.example.hotnewsapp.helper;

import java.util.Locale;

public class ApplicationConstant {
	public static String RSS_LINK = "https://ajax.googleapis.com/ajax/services/feed/load?v=2.0&q=http://vnexpress.net/rss/thoi-su.rss&num=50";
	public static final String USER_AGENT = "Mozilla/5.0 (Linux; U; Android "
			+ android.os.Build.VERSION.RELEASE + ";"
			+ Locale.getDefault().toString() + "; " + android.os.Build.DEVICE
			+ "/" + android.os.Build.ID + ")";
}
