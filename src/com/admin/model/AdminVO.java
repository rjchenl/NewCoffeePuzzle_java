package com.admin.model;

import java.io.Serializable;

public class AdminVO implements Serializable {

	private String admin_id;
	private String admin_acct;
	private String admin_pwd;
	private String admin_name;
	private String admin_email;
	private Integer admin_employed;
	private byte[] admin_img;

	public AdminVO(){}

	public AdminVO(String admin_id, String admin_acct, String admin_pwd, String admin_name, String admin_email, Integer admin_employed, byte[] admin_img){
		this.admin_id = admin_id;
		this.admin_acct = admin_acct;
		this.admin_pwd = admin_pwd;
		this.admin_name = admin_name;
		this.admin_email = admin_email;
		this.admin_employed = admin_employed;
		this.admin_img = admin_img;
	}

	public String getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}

	public String getAdmin_acct() {
		return admin_acct;
	}

	public void setAdmin_acct(String admin_acct) {
		this.admin_acct = admin_acct;
	}

	public String getAdmin_pwd() {
		return admin_pwd;
	}

	public void setAdmin_pwd(String admin_pwd) {
		this.admin_pwd = admin_pwd;
	}

	public String getAdmin_name() {
		return admin_name;
	}

	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}

	public String getAdmin_email() {
		return admin_email;
	}

	public void setAdmin_email(String admin_email) {
		this.admin_email = admin_email;
	}

	public Integer getAdmin_employed() {
		return admin_employed;
	}

	public void setAdmin_employed(Integer admin_employed) {
		this.admin_employed = admin_employed;
	}

	public byte[] getAdmin_img() {
		return admin_img;
	}

	public void setAdmin_img(byte[] admin_img) {
		this.admin_img = admin_img;
	}

}