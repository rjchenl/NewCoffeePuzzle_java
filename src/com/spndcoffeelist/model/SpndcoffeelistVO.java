package com.spndcoffeelist.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class SpndcoffeelistVO implements Serializable {

	private String list_id;
	private String spnd_id;
	private String mem_id;
	private String spnd_prod;
	private String store_id;
	private Integer list_amt;
	private Integer list_left;
	private Timestamp list_date;

	public SpndcoffeelistVO(){}

	public SpndcoffeelistVO(String list_id, String spnd_id, String mem_id, String spnd_prod, String store_id, Integer list_amt, Integer list_left, Timestamp list_date){
		this.list_id = list_id;
		this.spnd_id = spnd_id;
		this.mem_id = mem_id;
		this.spnd_prod = spnd_prod;
		this.store_id = store_id;
		this.list_amt = list_amt;
		this.list_left = list_left;
		this.list_date = list_date;
	}

	public String getList_id() {
		return list_id;
	}

	public void setList_id(String list_id) {
		this.list_id = list_id;
	}

	public String getSpnd_id() {
		return spnd_id;
	}

	public void setSpnd_id(String spnd_id) {
		this.spnd_id = spnd_id;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getSpnd_prod() {
		return spnd_prod;
	}

	public void setSpnd_prod(String spnd_prod) {
		this.spnd_prod = spnd_prod;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public Integer getList_amt() {
		return list_amt;
	}

	public void setList_amt(Integer list_amt) {
		this.list_amt = list_amt;
	}

	public Integer getList_left() {
		return list_left;
	}

	public void setList_left(Integer list_left) {
		this.list_left = list_left;
	}

	public Timestamp getList_date() {
		return list_date;
	}

	public void setList_date(Timestamp list_date) {
		this.list_date = list_date;
	}

}