package com.reply.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ReplyVO implements Serializable {

	private String reply_id;
	private String msg_id;
	private String mem_id;
	private String store_id;
	private String reply_content;
	private Timestamp reply_date;

	public ReplyVO(){}

	public ReplyVO(String reply_id, String msg_id, String mem_id, String store_id, String reply_content, Timestamp reply_date){
		this.reply_id = reply_id;
		this.msg_id = msg_id;
		this.mem_id = mem_id;
		this.store_id = store_id;
		this.reply_content = reply_content;
		this.reply_date = reply_date;
	}

	public String getReply_id() {
		return reply_id;
	}

	public void setReply_id(String reply_id) {
		this.reply_id = reply_id;
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

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getReply_content() {
		return reply_content;
	}

	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}

	public Timestamp getReply_date() {
		return reply_date;
	}

	public void setReply_date(Timestamp reply_date) {
		this.reply_date = reply_date;
	}

}