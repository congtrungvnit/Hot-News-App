package com.example.hotnewsapp;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import com.example.hotnewsapp.adapter.NewsContentAdapter;
import com.example.hotnewsapp.http.HttpRequest;
import com.example.hotnewsapp.http.HttpResponse;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class NewsContentFragment extends ListFragment {
	private static NewsContentAdapter mAdapter;
	private static ListView listView;
	private final static int UPDATENEWSCONTENT = 2;
	private MyHandler handler;
	private String newsLink;
	private static ArrayList<HashMap<String, String>> DATA;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DATA = new ArrayList<HashMap<String, String>>();
		handler = new MyHandler(getActivity());
		newsLink = getArguments().getString("url");
		new DownloadNewsContent().start();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_news_content,
				container, false);
		TextView title = (TextView) rootView.findViewById(R.id.title);
		TextView date = (TextView) rootView.findViewById(R.id.date);
		title.setText(getArguments().getString("title"));
		date.setText(getArguments().getString("date"));
		listView = (ListView) rootView.findViewById(android.R.id.list);
		return rootView;
	}

	private static void loadNewsContent(Context context) {
		mAdapter = new NewsContentAdapter(context, DATA);
		listView.setAdapter(mAdapter);
	}

	private static class MyHandler extends Handler {
		Context context;

		private MyHandler(Context context) {
			this.context = context;
		}

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == UPDATENEWSCONTENT) {
				loadNewsContent(context);
			}
		}
	};

	private class DownloadNewsContent extends Thread {
		private int checkLocation(String str, String strCheck) {
			return str.indexOf(strCheck);
		}

		@Override
		public void run() {
			HttpResponse response = null;
			String url = newsLink;
			HttpRequest request = HttpRequest.get(url);
			request.setUseCaches(false);
			try {
				response = request.execute();
				if (response.getStatusCode() == HttpURLConnection.HTTP_OK) {
					String text = response.getResponseString();
					Log.e("text", text);
					text = text.substring(text
							.indexOf("<div class=\"fck_detail\">"));
					text = text.replace("<span>", "");
					text = text.replace("</span>", "");
					text = text.replace("<em>", "");
					text = text.replace("</em>", "");
					text = text.replace("<strong>", "");
					text = text.replace("</strong>", "");
					text = text.replace("  ", " ");
					text = text.substring(0, text.indexOf("/div"));
					while (checkLocation(text, "src=") > 0
							|| checkLocation(text, "<p") > 0) {
						HashMap<String, String> dataItem = new HashMap<String, String>();
						if ((checkLocation(text, "src=") < checkLocation(text,
								"<p")) && checkLocation(text, "src=") > 0) {
							text = text.substring(text.indexOf("src=\"") + 5);
							dataItem.put("type", "img");
							dataItem.put("value",
									text.substring(0, text.indexOf("\">")));
						} else if ((checkLocation(text, "src=") > checkLocation(
								text, "<p")) && checkLocation(text, "<p") > 0) {
							text = text.substring(text.indexOf("<p") + 5);

							dataItem.put("type", "text");
							dataItem.put(
									"value",
									text.substring(text.indexOf(">") + 1,
											text.indexOf("</p>")));
						} else if (checkLocation(text, "src=") < 0) {
							text = text.substring(text.indexOf("<p") + 5);
							dataItem.put("type", "text");
							dataItem.put(
									"value",
									text.substring(text.indexOf(">") + 1,
											text.indexOf("</p>")));
						} else {
							text = text.substring(text.indexOf("src=\"") + 5);
							dataItem.put("type", "img");
							dataItem.put("value",
									text.substring(0, text.indexOf("\">")));
						}
						DATA.add(dataItem);
					}
				} else {
					Log.e("test", "ERR:" + response.getStatusCode());
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (response != null) {
					response.disconnect();
				}
			}
			if (handler != null) {
				Message msgObj = handler.obtainMessage();
				msgObj.what = UPDATENEWSCONTENT;
				handler.sendMessage(msgObj);

			}
		}
	}
}