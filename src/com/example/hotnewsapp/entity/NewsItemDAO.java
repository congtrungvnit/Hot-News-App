package com.example.hotnewsapp.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.hotnewsapp.db.DBOpenHelper;
import com.example.hotnewsapp.helper.ApplicationPreferences;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NewsItemDAO {

	private static NewsItemDAO instance;
	private DBOpenHelper mDbOpenHelper;
	private static Context context;

	public static NewsItemDAO getInstance(Context context) {
		if (instance == null) {
			instance = new NewsItemDAO(context);
			NewsItemDAO.context = context;
		}
		return instance;
	}

	private NewsItemDAO(Context context) {
		mDbOpenHelper = new DBOpenHelper(context, DBOpenHelper.DB_NAME, null,
				DBOpenHelper.DATABASE_VERSION);
	}

	public List<NewsItem> addNewsToLocalDB(JSONObject jsonObject) {
		List<NewsItem> listNews = new ArrayList<NewsItem>();
		try {
			JSONObject responseData = jsonObject.getJSONObject("responseData");
			JSONObject feed = responseData.getJSONObject("feed");
			JSONArray list = feed.getJSONArray("entries");
			for (int i = 0; i < list.length(); i++) {
				NewsItem newsItem = new NewsItem();
				String imageUrl = list.getJSONObject(i).getString("content");
				if (imageUrl.indexOf("src=\"") > 0) {
					imageUrl = imageUrl
							.substring(imageUrl.indexOf("src=\"") + 5);
					imageUrl = imageUrl.substring(0, imageUrl.indexOf("\">"));
				} else {
					imageUrl = "";
				}
				newsItem.setTitle(list.getJSONObject(i).getString("title"));
				newsItem.setDescription(list.getJSONObject(i).getString(
						"contentSnippet"));
				newsItem.setImageUrl(imageUrl);
				newsItem.setLink(list.getJSONObject(i).getString("link"));
				String dateSave = "";
				try {
					Date date = new SimpleDateFormat("EEE, d MMM yyyy h:m:s z",
							Locale.ENGLISH).parse(list.getJSONObject(i)
							.getString("publishedDate"));
					dateSave = Long.toString(date.getTime());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				newsItem.setDate(dateSave);
				listNews.add(newsItem);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return addNewsToLocalDB(listNews);
	}

	public List<NewsItem> addNewsToLocalDB(List<NewsItem> listNews) {
		int size = listNews.size();
		for (int i = size - 1; i >= 0; i--) {
			if (!isExistInLocalDB(listNews.get(i).getLink())) {
				addNewItemToLocalDB(listNews.get(i));
			} else {
				listNews.remove(i);
			}
		}

		return listNews;
	}

	public void addNewItemToLocalDB(NewsItem newsItem) {
		SQLiteDatabase sqLiteDatabase = mDbOpenHelper.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put(DBOpenHelper.NEWS_COL_DESCRIPTION,
				newsItem.getDescription());
		contentValues.put(DBOpenHelper.NEWS_COL_IMAGE_URL,
				newsItem.getImageUrl());
		contentValues.put(DBOpenHelper.NEWS_COL_LINK, newsItem.getLink());
		contentValues.put(DBOpenHelper.NEWS_COL_TITLE, newsItem.getTitle());
		contentValues.put(DBOpenHelper.NEWS_COL_DATE, newsItem.getDate());
		sqLiteDatabase
				.insert(DBOpenHelper.NEWS_TABLE_NAME, null, contentValues);
	}

	public Boolean isExistInLocalDB(String link) {
		String[] resultColumn = { DBOpenHelper.NEWS_COL_LINK };
		String where = DBOpenHelper.NEWS_COL_LINK + " like '%" + link + "%'";
		SQLiteDatabase sqLiteDatabase = mDbOpenHelper.getReadableDatabase();
		Cursor cursor = sqLiteDatabase.query(DBOpenHelper.NEWS_TABLE_NAME,
				resultColumn, where, null, null, null, null);
		return (cursor.getCount() > 0);
	}

	public List<NewsItem> getAllData() {
		List<NewsItem> result = new ArrayList<NewsItem>();
		String limit = Integer.toString(ApplicationPreferences
				.getNewsListSize(context));
		String[] resultColumn = { DBOpenHelper.NEWS_COL_LINK,
				DBOpenHelper.NEWS_COL_DESCRIPTION, DBOpenHelper.NEWS_COL_TITLE,
				DBOpenHelper.NEWS_COL_IMAGE_URL, DBOpenHelper.NEWS_COL_DATE };
		SQLiteDatabase sqLiteDatabase = mDbOpenHelper.getReadableDatabase();
		Cursor cursor = sqLiteDatabase.query(DBOpenHelper.NEWS_TABLE_NAME,
				resultColumn, null, null, null, null,
				DBOpenHelper.NEWS_COL_DATE + " DESC", limit);

		int linkColIndex = cursor
				.getColumnIndexOrThrow(DBOpenHelper.NEWS_COL_LINK);
		int descriptionColIndex = cursor
				.getColumnIndexOrThrow(DBOpenHelper.NEWS_COL_DESCRIPTION);
		int titleColIndex = cursor
				.getColumnIndexOrThrow(DBOpenHelper.NEWS_COL_TITLE);
		int imageColIndex = cursor
				.getColumnIndexOrThrow(DBOpenHelper.NEWS_COL_IMAGE_URL);
		int dateColIndex = cursor
				.getColumnIndexOrThrow(DBOpenHelper.NEWS_COL_DATE);

		while (cursor.moveToNext()) {
			String link = cursor.getString(linkColIndex);
			String description = cursor.getString(descriptionColIndex);
			String title = cursor.getString(titleColIndex);
			String image = cursor.getString(imageColIndex);
			String date = cursor.getString(dateColIndex);

			result.add(new NewsItem(title, description, link, image, date));
		}
		return result;
	}
	/*
	 * public Boolean isExist() { // String[] resultColumn = {
	 * DBOpenHelper.NEWS_COL_LINK }; // String where =
	 * DBOpenHelper.NEWS_COL_LINK + " = '" + link + "'"; SQLiteDatabase
	 * sqLiteDatabase = mDbOpenHelper.getReadableDatabase();
	 * 
	 * Cursor cursor = sqLiteDatabase.query(DBOpenHelper.NEWS_TABLE_NAME, null,
	 * null, null, null, null, null);
	 * 
	 * return (cursor.getCount() > 0); }
	 */
}
