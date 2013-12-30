package com.example.hotnewsapp.adapter;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotnewsapp.R;
import com.example.hotnewsapp.entity.KeyItem;
import com.example.hotnewsapp.entity.KeyItemDAO;

public class ListKeyAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<KeyItem> DATA;
	private KeyItemDAO keyItemDAO;
	private ListKeyAdapter adapter;

	public ListKeyAdapter(Context context, List<KeyItem> DATA) {
		mInflater = LayoutInflater.from(context);
		keyItemDAO = KeyItemDAO.getInstance(context);
		adapter = this;
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
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_newskeyword, null);
			holder = new ViewHolder();
			holder.keyContent = (TextView) convertView
					.findViewById(R.id.key_keyword);
			holder.keyDelete = (ImageView) convertView
					.findViewById(R.id.key_delete);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.keyContent.setText(DATA.get(position).getmContent());
		final View viewDelete = convertView;
		holder.keyDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				keyItemDAO
						.deleteKeyword(holder.keyContent.getText().toString());
				removeListItem(viewDelete, position, adapter);
			}
		});
		return convertView;
	}

	private void removeListItem(View rowView, final int positon,
			final ListKeyAdapter adapter) {
		final Animation animation = AnimationUtils.loadAnimation(
				rowView.getContext(), R.anim.rtlm);
		rowView.startAnimation(animation);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				DATA.remove(positon);
				adapter.notifyDataSetChanged();
			}
		}, 500);
		animation.cancel();

	}

	static class ViewHolder {
		TextView keyContent;
		ImageView keyDelete;
	}
}