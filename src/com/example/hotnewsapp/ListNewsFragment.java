package com.example.hotnewsapp;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.hotnewsapp.adapter.ListNewsAdapter;
import com.example.hotnewsapp.entity.NewsItem;
import com.example.hotnewsapp.entity.NewsItemDAO;
import com.example.hotnewsapp.helper.ApplicationConstant;
import com.example.hotnewsapp.http.HttpRequest;
import com.example.hotnewsapp.http.HttpResponse;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ListNewsFragment extends ListFragment {
	private static ListNewsAdapter mAdapter;
	private static List<NewsItem> DATA;
	private static ListView listView;
	private final static int UPDATENEWSLIST = 1;
	private MyHandler handler;
	private static NewsItemDAO newsItemDAO;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		newsItemDAO = NewsItemDAO.getInstance(getActivity());
		handler = new MyHandler(getActivity());
		new DownloadNewsList().start();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_listnews, container,
				false);
		listView = (ListView) rootView.findViewById(android.R.id.list);
		loadListNews(getActivity());
		return rootView;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		NewsContentFragment newFragment = new NewsContentFragment();
		Bundle args = new Bundle();
		args.putString("url", DATA.get(position).getLink());
		args.putString("title", DATA.get(position).getTitle());
		String date = usingDateFormatter(Long.parseLong(DATA.get(position)
				.getDate()));
		args.putString("date", date);
		newFragment.setArguments(args);
		FragmentTransaction transaction = getActivity()
				.getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment, newFragment);
		transaction.addToBackStack(null);
		transaction.commit();

	}

	private String usingDateFormatter(long input) {
		Date date = new Date(input);
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MMM/dd hh:mm:ss z",
				Locale.US);
		sdf.setCalendar(cal);
		cal.setTime(date);
		return sdf.format(date);

	}

	private static void loadListNews(Context context) {
		DATA = newsItemDAO.getAllData();
		mAdapter = new ListNewsAdapter(context, DATA);
		listView.setAdapter(mAdapter);
	}

	private static class MyHandler extends Handler {
		Context context;

		private MyHandler(Context context) {
			this.context = context;
		}

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == UPDATENEWSLIST) {
				loadListNews(context);
			}
		}
	};

	private class DownloadNewsList extends Thread {

		@Override
		public void run() {
			HttpResponse response = null;
			String url = ApplicationConstant.RSS_LINK;
			HttpRequest request = HttpRequest.get(url);
			request.setUseCaches(false);

			try {
				response = request.execute();

				if (response.getStatusCode() == HttpURLConnection.HTTP_OK) {
					JSONObject jsonObject = new JSONObject(
							response.getResponseString());
					int addCount = newsItemDAO.addNewsToLocalDB(jsonObject);
					if (handler != null && addCount > 0) {
						Message msgObj = handler.obtainMessage();
						msgObj.what = UPDATENEWSLIST;
						handler.sendMessage(msgObj);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}
}