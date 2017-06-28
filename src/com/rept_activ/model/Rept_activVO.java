package com.rept_activ.model;

import java.io.Serializable;

public class Rept_activVO implements Serializable {

	private String activ_id;
	private String mem_id;
	private String repo_rsn;
	private Integer repo_rev;

	public Rept_activVO(){}

	public Rept_activVO(String activ_id, String mem_id, String repo_rsn, Integer repo_rev){
		this.activ_id = activ_id;
		this.mem_id = mem_id;
		this.repo_rsn = repo_rsn;
		this.repo_rev = repo_rev;
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

	public String getRepo_rsn() {
		return repo_rsn;
	}

	public void setRepo_rsn(String repo_rsn) {
		this.repo_rsn = repo_rsn;
	}

	public Integer getRepo_rev() {
		return repo_rev;
	}

	public void setRepo_rev(Integer repo_rev) {
		this.repo_rev = repo_rev;
	}

}