package com.report.model;

import java.io.Serializable;

public class ReportVO implements Serializable {

	private String mem_id;
	private String rnr_id;
	private Integer rept_verf;
	private String rept_rsn;

	public ReportVO(){}

	public ReportVO(String mem_id, String rnr_id, Integer rept_verf, String rept_rsn){
		this.mem_id = mem_id;
		this.rnr_id = rnr_id;
		this.rept_verf = rept_verf;
		this.rept_rsn = rept_rsn;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getRnr_id() {
		return rnr_id;
	}

	public void setRnr_id(String rnr_id) {
		this.rnr_id = rnr_id;
	}

	public Integer getRept_verf() {
		return rept_verf;
	}

	public void setRept_verf(Integer rept_verf) {
		this.rept_verf = rept_verf;
	}

	public String getRept_rsn() {
		return rept_rsn;
	}

	public void setRept_rsn(String rept_rsn) {
		this.rept_rsn = rept_rsn;
	}

}