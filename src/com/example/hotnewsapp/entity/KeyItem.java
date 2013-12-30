package com.example.hotnewsapp.entity;

public class KeyItem {
	private String mId;
	private String mStatus;
	private String mContent;
	private String mDate;

	public KeyItem() {
	}

	public KeyItem(String id, String status, String content, String date) {
		this.mId = id;
		this.mStatus = status;
		this.mContent = content;
		this.mDate = date;
	}

	public KeyItem(String status, String content, String date) {
		this.mStatus = status;
		this.mContent = content;
		this.mDate = date;
	}

	public KeyItem(String status, String content) {
		this.mStatus = status;
		this.mContent = content;
	}

	public String getmId() {
		return mId;
	}

	public void setmId(String mId) {
		this.mId = mId;
	}

	public String getmStatus() {
		return mStatus;
	}

	public void setmStatus(String mStatus) {
		this.mStatus = mStatus;
	}

	public String getmContent() {
		return mContent;
	}

	public void setmContent(String mContent) {
		this.mContent = mContent;
	}

	public String getmDate() {
		return mDate;
	}

	public void setmDate(String mDate) {
		this.mDate = mDate;
	}

}
