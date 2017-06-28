package com.store_tag.model;

import java.io.Serializable;

public class Store_tagVO implements Serializable {

	private String store_id;
	private String tag_id;
	private Integer tag_num;

	public Store_tagVO(){}

	public Store_tagVO(String store_id, String tag_id, Integer tag_num){
		this.store_id = store_id;
		this.tag_id = tag_id;
		this.tag_num = tag_num;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getTag_id() {
		return tag_id;
	}

	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}

	public Integer getTag_num() {
		return tag_num;
	}

	public void setTag_num(Integer tag_num) {
		this.tag_num = tag_num;
	}

}