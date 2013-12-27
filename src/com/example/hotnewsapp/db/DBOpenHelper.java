package com.example.hotnewsapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "NewsDB.db";
	public static final String NEWS_TABLE_NAME = "NewsTable";
	public static final String KEYS_TABLE_NAME = "KeysTable";
	public static final int DATABASE_VERSION = 2;

	public static final String NEWS_COL_TITLE = "title";
	public static final String NEWS_COL_DESCRIPTION = "Description";
	public static final String NEWS_COL_LINK = "link";
	public static final String NEWS_COL_DATE = "date";
	public static final String NEWS_COL_IMAGE_URL = "image_url";

	public static final String KEY_COL_ID = "id";
	public static final String KEY_COL_STATUS = "status";
	public static final String KEY_COL_CONTENT = "content";
	public static final String KEY_COL_DATE = "date";

	private static final String CREATE_NEWS_TABLE = "create table "
			+ NEWS_TABLE_NAME + " (" + NEWS_COL_TITLE + " text not null, "
			+ NEWS_COL_DESCRIPTION + " text not null, " + NEWS_COL_DATE
			+ " text not null, " + NEWS_COL_LINK + " text not null, "
			+ NEWS_COL_IMAGE_URL + " text not null);";
	private static final String CREATE_KEYS_TABLE = "create table "
			+ KEYS_TABLE_NAME + " (" + KEY_COL_ID
			+ " integer primary key autoincrement, " + KEY_COL_STATUS
			+ " text not null, " + KEY_COL_CONTENT + " text not null, "
			+ KEY_COL_DATE + " TIMESTAMP NOT NULL DEFAULT current_timestamp);";

	public DBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_NEWS_TABLE);
		db.execSQL(CREATE_KEYS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + NEWS_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + KEYS_TABLE_NAME);
		onCreate(db);
	}

}
