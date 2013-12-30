package com.example.hotnewsapp.adapter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import com.example.hotnewsapp.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewsContentAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private ArrayList<HashMap<String, String>> DATA;
	private Drawable drawable;

	public NewsContentAdapter(Context context,
			ArrayList<HashMap<String, String>> DATA) {
		mInflater = LayoutInflater.from(context);
		drawable = context.getResources().getDrawable(R.drawable.ic_launcher);
		this.DATA = DATA;
	}

	public int getCount() {
		return DATA.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_newscontent, null);
			holder = new ViewHolder();
			holder.newsContent = (TextView) convertView
					.findViewById(R.id.news_content);
			holder.newsImage = (com.example.hotnewsapp.imageutil.PhotoView) convertView
					.findViewById(R.id.news_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (DATA.get(position).get("type").equals("text")) {
			holder.newsImage.setVisibility(View.GONE);
			holder.newsContent.setVisibility(View.VISIBLE);
			holder.newsContent.setText(DATA.get(position).get("value"));

		} else {

			holder.newsContent.setVisibility(View.GONE);
			if (!DATA.get(position).get("value").equals("")) {
				holder.newsImage.setVisibility(View.VISIBLE);
				try {
					URL localURL = new URL(DATA.get(position).get("value"));
					holder.newsImage.setImageURL(localURL, true, drawable);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				holder.newsImage.setVisibility(View.GONE);
			}
		}
		return convertView;
	}

	static class ViewHolder {
		TextView newsContent;
		com.example.hotnewsapp.imageutil.PhotoView newsImage;
	}
}