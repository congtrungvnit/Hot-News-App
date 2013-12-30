package com.example.hotnewsapp.adapter;

import java.util.List;

import com.example.hotnewsapp.R;
import com.example.hotnewsapp.entity.NewsItem;
import com.example.hotnewsapp.file.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListNewsAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<NewsItem> DATA;
	ImageLoader loader;

	public ListNewsAdapter(Context context, List<NewsItem> DATA) {
		mInflater = LayoutInflater.from(context);
		loader = new ImageLoader(context);
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
			convertView = mInflater.inflate(R.layout.item_listnews, null);
			holder = new ViewHolder();
			holder.newsTitle = (TextView) convertView
					.findViewById(R.id.news_title);
			holder.newsContent = (TextView) convertView
					.findViewById(R.id.news_content);
			holder.newsImage = (ImageView) convertView
					.findViewById(R.id.news_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.newsTitle.setText(DATA.get(position).getTitle());
		holder.newsContent.setText(DATA.get(position).getDescription());
		if (!DATA.get(position).getImageUrl().equals(""))
			loader.DisplayImage(DATA.get(position).getImageUrl(),
					holder.newsImage);
		return convertView;
	}

	static class ViewHolder {
		TextView newsTitle;
		TextView newsContent;
		ImageView newsImage;
	}
}