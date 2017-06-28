package com.spndcoffee.model;

import java.io.Serializable;
import java.sql.Date;

public class SpndcoffeeVO implements Serializable {

	private String spnd_id;
	private String store_id;
	private String spnd_name;
	private String spnd_prod;
	private Date spnd_enddate;
	private Integer spnd_amt;
	private byte[] spnd_img;

	public SpndcoffeeVO(){}

	public SpndcoffeeVO(String spnd_id, String store_id, String spnd_name, String spnd_prod, Date spnd_enddate, Integer spnd_amt, byte[] spnd_img){
		this.spnd_id = spnd_id;
		this.store_id = store_id;
		this.spnd_name = spnd_name;
		this.spnd_prod = spnd_prod;
		this.spnd_enddate = spnd_enddate;
		this.spnd_amt = spnd_amt;
		this.spnd_img = spnd_img;
	}

	public String getSpnd_id() {
		return spnd_id;
	}

	public void setSpnd_id(String spnd_id) {
		this.spnd_id = spnd_id;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getSpnd_name() {
		return spnd_name;
	}

	public void setSpnd_name(String spnd_name) {
		this.spnd_name = spnd_name;
	}

	public String getSpnd_prod() {
		return spnd_prod;
	}

	public void setSpnd_prod(String spnd_prod) {
		this.spnd_prod = spnd_prod;
	}

	public Date getSpnd_enddate() {
		return spnd_enddate;
	}

	public void setSpnd_enddate(Date spnd_enddate) {
		this.spnd_enddate = spnd_enddate;
	}

	public Integer getSpnd_amt() {
		return spnd_amt;
	}

	public void setSpnd_amt(Integer spnd_amt) {
		this.spnd_amt = spnd_amt;
	}

	public byte[] getSpnd_img() {
		return spnd_img;
	}

	public void setSpnd_img(byte[] spnd_img) {
		this.spnd_img = spnd_img;
	}

}