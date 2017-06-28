package com.activity.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ActivityVO implements Serializable {

	private String activ_id;
	private String mem_id;
	private String store_id;
	private String activ_name;
	private Timestamp activ_starttime;
	private Timestamp activ_endtime;
	private Timestamp activ_expire;
	private byte[] activ_img;
	private String activ_summary;
	private String activ_intro;
	private Integer activ_num;
	private Integer activ_store_cfm;

	public ActivityVO(){}

	public ActivityVO(String activ_id, String mem_id, String store_id, String activ_name, Timestamp activ_starttime, Timestamp activ_endtime, Timestamp activ_expire, byte[] activ_img, String activ_summary, String activ_intro, Integer activ_num, Integer activ_store_cfm){
		this.activ_id = activ_id;
		this.mem_id = mem_id;
		this.store_id = store_id;
		this.activ_name = activ_name;
		this.activ_starttime = activ_starttime;
		this.activ_endtime = activ_endtime;
		this.activ_expire = activ_expire;
		this.activ_img = activ_img;
		this.activ_summary = activ_summary;
		this.activ_intro = activ_intro;
		this.activ_num = activ_num;
		this.activ_store_cfm = activ_store_cfm;
	}

	public String getActiv_id() {
		return activ_id;
	}

	public void setActiv_id(String activ_id) {
		this.activ_id = activ_id;
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

	public String getActiv_name() {
		return activ_name;
	}

	public void setActiv_name(String activ_name) {
		this.activ_name = activ_name;
	}

	public Timestamp getActiv_starttime() {
		return activ_starttime;
	}

	public void setActiv_starttime(Timestamp activ_starttime) {
		this.activ_starttime = activ_starttime;
	}

	public Timestamp getActiv_endtime() {
		return activ_endtime;
	}

	public void setActiv_endtime(Timestamp activ_endtime) {
		this.activ_endtime = activ_endtime;
	}

	public Timestamp getActiv_expire() {
		return activ_expire;
	}

	public void setActiv_expire(Timestamp activ_expire) {
		this.activ_expire = activ_expire;
	}

	public byte[] getActiv_img() {
		return activ_img;
	}

	public void setActiv_img(byte[] activ_img) {
		this.activ_img = activ_img;
	}

	public String getActiv_summary() {
		return activ_summary;
	}

	public void setActiv_summary(String activ_summary) {
		this.activ_summary = activ_summary;
	}

	public String getActiv_intro() {
		return activ_intro;
	}

	public void setActiv_intro(String activ_intro) {
		this.activ_intro = activ_intro;
	}

	public Integer getActiv_num() {
		return activ_num;
	}

	public void setActiv_num(Integer activ_num) {
		this.activ_num = activ_num;
	}

	public Integer getActiv_store_cfm() {
		return activ_store_cfm;
	}

	public void setActiv_store_cfm(Integer activ_store_cfm) {
		this.activ_store_cfm = activ_store_cfm;
	}

}