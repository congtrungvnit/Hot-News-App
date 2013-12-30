package com.example.hotnewsapp.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.hotnewsapp.db.DBOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class KeyItemDAO {

	private static KeyItemDAO instance;
	private DBOpenHelper mDbOpenHelper;

	public static KeyItemDAO getInstance(Context context) {
		if (instance == null) {
			instance = new KeyItemDAO(context);
		}
		return instance;
	}

	private KeyItemDAO(Context context) {
		mDbOpenHelper = new DBOpenHelper(context, DBOpenHelper.DB_NAME, null,
				DBOpenHelper.DATABASE_VERSION);
	}

	public void addKeyItemToLocalDB(KeyItem keyItem) {
		SQLiteDatabase sqLiteDatabase = mDbOpenHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DBOpenHelper.KEY_COL_CONTENT, keyItem.getmContent());
		contentValues.put(DBOpenHelper.KEY_COL_STATUS, keyItem.getmStatus());
		sqLiteDatabase
				.insert(DBOpenHelper.KEYS_TABLE_NAME, null, contentValues);
	}

	public Boolean isExistInLocalDB(String key) {
		String[] resultColumn = { DBOpenHelper.KEY_COL_CONTENT };
		String where = DBOpenHelper.KEY_COL_CONTENT + " = '" + key + "'";
		SQLiteDatabase sqLiteDatabase = mDbOpenHelper.getReadableDatabase();
		Cursor cursor = sqLiteDatabase.query(DBOpenHelper.KEYS_TABLE_NAME,
				resultColumn, where, null, null, null, null);
		return (cursor.getCount() > 0);
	}

	public void deleteKeyword(String content) {
		SQLiteDatabase sqLiteDatabase = mDbOpenHelper.getWritableDatabase();
		sqLiteDatabase
				.delete(DBOpenHelper.KEYS_TABLE_NAME,
						DBOpenHelper.KEY_COL_CONTENT + " = ?",
						new String[] { content });
	}

	public List<KeyItem> getAllData() {
		List<KeyItem> result = new ArrayList<KeyItem>();
		String[] resultColumn = { DBOpenHelper.KEY_COL_ID,
				DBOpenHelper.KEY_COL_STATUS, DBOpenHelper.KEY_COL_CONTENT,
				DBOpenHelper.KEY_COL_DATE };
		SQLiteDatabase sqLiteDatabase = mDbOpenHelper.getReadableDatabase();
		Cursor cursor = sqLiteDatabase.query(DBOpenHelper.KEYS_TABLE_NAME,
				resultColumn, null, null, null, null,
				DBOpenHelper.NEWS_COL_DATE + " DESC");

		int contentColIndex = cursor
				.getColumnIndexOrThrow(DBOpenHelper.KEY_COL_CONTENT);
		int dateColIndex = cursor
				.getColumnIndexOrThrow(DBOpenHelper.KEY_COL_DATE);
		int idColIndex = cursor.getColumnIndexOrThrow(DBOpenHelper.KEY_COL_ID);
		int statusColIndex = cursor
				.getColumnIndexOrThrow(DBOpenHelper.KEY_COL_STATUS);

		while (cursor.moveToNext()) {
			String content = cursor.getString(contentColIndex);
			String date = cursor.getString(dateColIndex);
			String id = cursor.getString(idColIndex);
			String status = cursor.getString(statusColIndex);
			result.add(new KeyItem(id, status, content, date));
		}
		return result;
	}

	/*public ArrayList<HashMap<String, String>> checkKey(List<NewsItem> listNews) {
		ArrayList<HashMap<String, String>> DATA = new ArrayList<HashMap<String, String>>();
		List<KeyItem> listKey = getAllData();
		for (int i = 0; i < listKey.size(); i++) {
			String key = listKey.get(i).getmContent();
			int countKey = 0;
			HashMap<String, String> searchItem = new HashMap<String, String>();
			for (int j = 0; j < listNews.size(); j++) {
				if (listNews.get(j).getDescription()
						.indexOf(listKey.get(i).getmContent()) > 0) {
					countKey++;
				}
			}

		}
		return DATA;
	}*/
}
