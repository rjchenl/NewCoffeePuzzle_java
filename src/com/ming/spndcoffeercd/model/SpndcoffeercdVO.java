package com.ming.spndcoffeercd.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class SpndcoffeercdVO implements Serializable {

	private String rcd_id;
	private String list_id;
	private Integer single_amt;
	private Timestamp rcd_date;

	public SpndcoffeercdVO(){}

	public SpndcoffeercdVO(String rcd_id, String list_id, Integer single_amt, Timestamp rcd_date){
		this.rcd_id = rcd_id;
		this.list_id = list_id;
		this.single_amt = single_amt;
		this.rcd_date = rcd_date;
	}

	public String getRcd_id() {
		return rcd_id;
	}

	public void setRcd_id(String rcd_id) {
		this.rcd_id = rcd_id;
	}

	public String getList_id() {
		return list_id;
	}

	public void setList_id(String list_id) {
		this.list_id = list_id;
	}

	public Integer getSingle_amt() {
		return single_amt;
	}

	public void setSingle_amt(Integer single_amt) {
		this.single_amt = single_amt;
	}

	public Timestamp getRcd_date() {
		return rcd_date;
	}

	public void setRcd_date(Timestamp rcd_date) {
		this.rcd_date = rcd_date;
	}

}