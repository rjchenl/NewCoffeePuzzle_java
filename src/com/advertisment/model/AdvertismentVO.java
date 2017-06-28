package com.advertisment.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class AdvertismentVO implements Serializable {

	private String ad_id;
	private String ad_title;
	private String ad_content;
	private byte[] ad_img;
	private Timestamp ad_date;
	private Integer ad_status;

	public AdvertismentVO(){}

	public AdvertismentVO(String ad_id, String ad_title, String ad_content, byte[] ad_img, Timestamp ad_date, Integer ad_status){
		this.ad_id = ad_id;
		this.ad_title = ad_title;
		this.ad_content = ad_content;
		this.ad_img = ad_img;
		this.ad_date = ad_date;
		this.ad_status = ad_status;
	}

	public String getAd_id() {
		return ad_id;
	}

	public void setAd_id(String ad_id) {
		this.ad_id = ad_id;
	}

	public String getAd_title() {
		return ad_title;
	}

	public void setAd_title(String ad_title) {
		this.ad_title = ad_title;
	}

	public String getAd_content() {
		return ad_content;
	}

	public void setAd_content(String ad_content) {
		this.ad_content = ad_content;
	}

	public byte[] getAd_img() {
		return ad_img;
	}

	public void setAd_img(byte[] ad_img) {
		this.ad_img = ad_img;
	}

	public Timestamp getAd_date() {
		return ad_date;
	}

	public void setAd_date(Timestamp ad_date) {
		this.ad_date = ad_date;
	}

	public Integer getAd_status() {
		return ad_status;
	}

	public void setAd_status(Integer ad_status) {
		this.ad_status = ad_status;
	}

}