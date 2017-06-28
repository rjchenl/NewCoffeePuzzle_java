package com.fav_store.model;

import java.io.Serializable;

public class Fav_storeVO implements Serializable {

	private String mem_id;
	private String store_id;

	public Fav_storeVO(){}

	public Fav_storeVO(String mem_id, String store_id){
		this.mem_id = mem_id;
		this.store_id = store_id;
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

}