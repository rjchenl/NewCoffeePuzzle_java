package com.news.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class NewsVO implements Serializable {

	private String news_id;
	private String store_id;
	private String news_title;
	private String news_content;
	private byte[] news_img;
	private Timestamp news_date;
	private String news_class;
	private Integer news_top;
	private Integer news_pass;

	public NewsVO(){}

	public NewsVO(String news_id, String store_id, String news_title, String news_content, byte[] news_img, Timestamp news_date, String news_class, Integer news_top, Integer news_pass){
		this.news_id = news_id;
		this.store_id = store_id;
		this.news_title = news_title;
		this.news_content = news_content;
		this.news_img = news_img;
		this.news_date = news_date;
		this.news_class = news_class;
		this.news_top = news_top;
		this.news_pass = news_pass;
	}

	public String getNews_id() {
		return news_id;
	}

	public void setNews_id(String news_id) {
		this.news_id = news_id;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getNews_title() {
		return news_title;
	}

	public void setNews_title(String news_title) {
		this.news_title = news_title;
	}

	public String getNews_content() {
		return news_content;
	}

	public void setNews_content(String news_content) {
		this.news_content = news_content;
	}

	public byte[] getNews_img() {
		return news_img;
	}

	public void setNews_img(byte[] news_img) {
		this.news_img = news_img;
	}

	public Timestamp getNews_date() {
		return news_date;
	}

	public void setNews_date(Timestamp news_date) {
		this.news_date = news_date;
	}

	public String getNews_class() {
		return news_class;
	}

	public void setNews_class(String news_class) {
		this.news_class = news_class;
	}

	public Integer getNews_top() {
		return news_top;
	}

	public void setNews_top(Integer news_top) {
		this.news_top = news_top;
	}

	public Integer getNews_pass() {
		return news_pass;
	}

	public void setNews_pass(Integer news_pass) {
		this.news_pass = news_pass;
	}

}