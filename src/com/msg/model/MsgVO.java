package com.msg.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class MsgVO implements Serializable {

	private String msg_id;
	private String mem_id;
	private String prod_id;
	private String msg_content;
	private Timestamp msg_date;

	public MsgVO(){}

	public MsgVO(String msg_id, String mem_id, String prod_id, String msg_content, Timestamp msg_date){
		this.msg_id = msg_id;
		this.mem_id = mem_id;
		this.prod_id = prod_id;
		this.msg_content = msg_content;
		this.msg_date = msg_date;
	}

	public String getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getProd_id() {
		return prod_id;
	}

	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}

	public String getMsg_content() {
		return msg_content;
	}

	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
	}

	public Timestamp getMsg_date() {
		return msg_date;
	}

	public void setMsg_date(Timestamp msg_date) {
		this.msg_date = msg_date;
	}

}