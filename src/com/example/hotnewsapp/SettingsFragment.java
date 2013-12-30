package com.example.hotnewsapp;

import java.util.List;

import com.example.hotnewsapp.adapter.ListKeyAdapter;
import com.example.hotnewsapp.entity.KeyItem;
import com.example.hotnewsapp.entity.KeyItemDAO;
import com.example.hotnewsapp.helper.ApplicationPreferences;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class SettingsFragment extends Fragment {
	private static ListKeyAdapter mAdapter;
	private static ListView listView;
	private EditText mEdtNewsRow;
	private EditText mEdtKeyword;
	private Button mBtnAddKeyword;
	private EditText mEdtNewsUpdateTime;
	private static List<KeyItem> DATA;
	private static KeyItemDAO keyItemDAO;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_settings, container,
				false);
		keyItemDAO = KeyItemDAO.getInstance(getActivity());
		listView = (ListView) rootView.findViewById(android.R.id.list);
		mEdtNewsRow = (EditText) rootView.findViewById(R.id.edt_newssize);
		mEdtKeyword = (EditText) rootView.findViewById(R.id.edt_newskeyword);
		mEdtNewsUpdateTime = (EditText) rootView
				.findViewById(R.id.edt_newsupdate);
		mBtnAddKeyword = (Button) rootView.findViewById(R.id.btn_add);

		setting();
		loadListKeys(getActivity());
		return rootView;
	}

	private void setting() {
		mEdtNewsRow.setText(String.valueOf(ApplicationPreferences
				.getNewsListSize(getActivity())));
		mEdtNewsUpdateTime.setText(String.valueOf(ApplicationPreferences
				.getNewsUpdateTime(getActivity())));
		mBtnAddKeyword.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!mEdtKeyword.getText().toString().equals("")) {
					if (!keyItemDAO.isExistInLocalDB(mEdtKeyword.getText()
							.toString())) {
						KeyItem newKey = new KeyItem("1", mEdtKeyword.getText()
								.toString());
						keyItemDAO.addKeyItemToLocalDB(newKey);
						DATA.add(0, newKey);
						mAdapter.notifyDataSetChanged();
					} else {
						mEdtKeyword.setError(getResources().getText(
								R.string.err_ex_key));
					}
				} else {
					mEdtKeyword.setError(getResources().getText(
							R.string.err_null_key));
				}
			}
		});
		mEdtKeyword.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				mEdtKeyword.setError(null);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		mEdtNewsRow.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == false) {
					if (mEdtNewsRow.getText().toString().equals("")) {
						mEdtNewsRow.setText(String
								.valueOf(ApplicationPreferences
										.getNewsListSize(getActivity())));
					} else {
						ApplicationPreferences.saveNewsListSize(getActivity(),
								Integer.parseInt(mEdtNewsRow.getText()
										.toString()));
					}
				}
			}
		});
	}

	private static void loadListKeys(Context context) {
		DATA = keyItemDAO.getAllData();
		mAdapter = new ListKeyAdapter(context, DATA);
		listView.setAdapter(mAdapter);
	}
}
