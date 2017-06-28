package com.photo_store.model;

import java.io.Serializable;

public class Photo_storeVO implements Serializable {

	private String photo_id;
	private byte[] photo;
	private String store_id;
	private String mem_id;
	private String photo_desc;

	public Photo_storeVO(){}

	public Photo_storeVO(String photo_id, byte[] photo, String store_id, String mem_id, String photo_desc){
		this.photo_id = photo_id;
		this.photo = photo;
		this.store_id = store_id;
		this.mem_id = mem_id;
		this.photo_desc = photo_desc;
	}

	public String getPhoto_id() {
		return photo_id;
	}

	public void setPhoto_id(String photo_id) {
		this.photo_id = photo_id;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
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

	public String getPhoto_desc() {
		return photo_desc;
	}

	public void setPhoto_desc(String photo_desc) {
		this.photo_desc = photo_desc;
	}

}