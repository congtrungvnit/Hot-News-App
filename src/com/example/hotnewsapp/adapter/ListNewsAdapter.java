package com.example.hotnewsapp.adapter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.example.hotnewsapp.R;
import com.example.hotnewsapp.entity.NewsItem;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListNewsAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<NewsItem> DATA;
	private Drawable drawable;

	public ListNewsAdapter(Context context, List<NewsItem> DATA) {
		mInflater = LayoutInflater.from(context);
		this.DATA = DATA;
		drawable = context.getResources().getDrawable(R.drawable.ic_launcher);
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
			convertView = mInflater.inflate(R.layout.item_listnews, null);
			holder = new ViewHolder();
			holder.newsTitle = (TextView) convertView
					.findViewById(R.id.news_title);
			holder.newsContent = (TextView) convertView
					.findViewById(R.id.news_content);
			holder.newsImage = (com.example.hotnewsapp.imageutil.PhotoView) convertView
					.findViewById(R.id.news_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.newsTitle.setText(DATA.get(position).getTitle());
		holder.newsContent.setText(DATA.get(position).getDescription());
		if (!DATA.get(position).getImageUrl().equals("")) {
			/*
			 * loader.DisplayImage(DATA.get(position).getImageUrl(),
			 * holder.newsImage);
			 */

			try {
				URL localURL = new URL(DATA.get(position).getImageUrl());
				holder.newsImage.setImageURL(localURL, true, drawable);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return convertView;
	}

	static class ViewHolder {
		TextView newsTitle;
		TextView newsContent;
		com.example.hotnewsapp.imageutil.PhotoView newsImage;
	}
}