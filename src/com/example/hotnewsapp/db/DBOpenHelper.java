package com.example.hotnewsapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "NewsDB.db";
	public static final String NEWS_TABLE_NAME = "NewsTable";
	public static final int DATABASE_VERSION = 1;

	public static final String NEWS_COL_TITLE = "title";
	public static final String NEWS_COL_DESCRIPTION = "Description";
	public static final String NEWS_COL_LINK = "link";
	public static final String NEWS_COL_DATE = "date";
	public static final String NEWS_COL_IMAGE_URL = "image_url";

	private static final String CREATE_NEWS_TABLE = "create table "
			+ NEWS_TABLE_NAME + " (" + NEWS_COL_TITLE + " text not null, "
			+ NEWS_COL_DESCRIPTION + " text not null, " + NEWS_COL_DATE
			+ " text not null, " + NEWS_COL_LINK + " text not null, "
			+ NEWS_COL_IMAGE_URL + " text not null);";

	public DBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_NEWS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + NEWS_TABLE_NAME);
		onCreate(db);
	}

}
