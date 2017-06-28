package com.rate_n_rev.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Rate_n_revVO implements Serializable {

	private String rnr_id;
	private String mem_id;
	private String store_id;
	private Integer rnr_rate;
	private String rnr_rev;
	private Timestamp rnr_date;

	public Rate_n_revVO(){}

	public Rate_n_revVO(String rnr_id, String mem_id, String store_id, Integer rnr_rate, String rnr_rev, Timestamp rnr_date){
		this.rnr_id = rnr_id;
		this.mem_id = mem_id;
		this.store_id = store_id;
		this.rnr_rate = rnr_rate;
		this.rnr_rev = rnr_rev;
		this.rnr_date = rnr_date;
	}

	public String getRnr_id() {
		return rnr_id;
	}

	public void setRnr_id(String rnr_id) {
		this.rnr_id = rnr_id;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public Integer getRnr_rate() {
		return rnr_rate;
	}

	public void setRnr_rate(Integer rnr_rate) {
		this.rnr_rate = rnr_rate;
	}

	public String getRnr_rev() {
		return rnr_rev;
	}

	public void setRnr_rev(String rnr_rev) {
		this.rnr_rev = rnr_rev;
	}

	public Timestamp getRnr_date() {
		return rnr_date;
	}

	public void setRnr_date(Timestamp rnr_date) {
		this.rnr_date = rnr_date;
	}

}