package com.example.hotnewsapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class ApplicationPreferences {

	private static final String NEWS_LIST_SIZE = "newslistsize";
	private static final String NEWS_UPDATE_TIME = "newsupdatetime";
	private static SharedPreferences mAppPreferences;
	private static SharedPreferences.Editor mEditor;
	private static int HALFDAY = 60 * 12;

	private ApplicationPreferences() {

	}

	public static void initPrefs(Context context) {
		if (mAppPreferences == null) {
			mAppPreferences = context.getSharedPreferences("Session",
					Context.MODE_PRIVATE);
		}
	}

	public static void saveNewsListSize(Context context, int size) {
		initPrefs(context);
		mEditor = mAppPreferences.edit();
		mEditor.putInt(NEWS_LIST_SIZE, size);
		mEditor.commit();
	}

	public static int getNewsListSize(Context context) {
		initPrefs(context);
		return mAppPreferences.getInt(NEWS_LIST_SIZE, 50);
	}

	public static void saveNewsUpdateTime(Context context, int time) {
		initPrefs(context);
		mEditor = mAppPreferences.edit();
		mEditor.putInt(NEWS_UPDATE_TIME, time);
		mEditor.commit();
	}

	public static int getNewsUpdateTime(Context context) {
		initPrefs(context);
		return mAppPreferences.getInt(NEWS_UPDATE_TIME, HALFDAY);
	}
}
