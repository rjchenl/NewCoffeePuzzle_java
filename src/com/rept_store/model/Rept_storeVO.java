package com.rept_store.model;

import java.io.Serializable;

public class Rept_storeVO implements Serializable {

	private String store_id;
	private String mem_id;
	private String rept_rsn;
	private Integer rept_rev;

	public Rept_storeVO(){}

	public Rept_storeVO(String store_id, String mem_id, String rept_rsn, Integer rept_rev){
		this.store_id = store_id;
		this.mem_id = mem_id;
		this.rept_rsn = rept_rsn;
		this.rept_rev = rept_rev;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getRept_rsn() {
		return rept_rsn;
	}

	public void setRept_rsn(String rept_rsn) {
		this.rept_rsn = rept_rsn;
	}

	public Integer getRept_rev() {
		return rept_rev;
	}

	public void setRept_rev(Integer rept_rev) {
		this.rept_rev = rept_rev;
	}

}