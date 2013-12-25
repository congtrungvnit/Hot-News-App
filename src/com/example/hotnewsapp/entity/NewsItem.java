package com.example.hotnewsapp.entity;

public class NewsItem {
	private String mTitle;
	private String mDescription;
	private String mLink;
	private String mImageUrl;
	private String mDate;

	public NewsItem() {
	}

	public NewsItem(String title, String description, String link,
			String imageUrl, String date) {
		this.mTitle = title;
		this.mDescription = description;
		this.mLink = link;
		this.mImageUrl = imageUrl;
		this.mDate = date;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String mDescription) {
		this.mDescription = mDescription;
	}

	public String getLink() {
		return mLink;
	}

	public void setLink(String mLink) {
		this.mLink = mLink;
	}

	public String getImageUrl() {
		return mImageUrl;
	}

	public void setImageUrl(String mImageUrl) {
		this.mImageUrl = mImageUrl;
	}

	public String getDate() {
		return mDate;
	}

	public void setDate(String mDate) {
		this.mDate = mDate;
	}

}
