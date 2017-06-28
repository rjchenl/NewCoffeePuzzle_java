package com.participant.model;

import java.io.Serializable;

public class ParticipantVO implements Serializable {

	private String activ_id;
	private String mem_id;

	public ParticipantVO(){}

	public ParticipantVO(String activ_id, String mem_id){
		this.activ_id = activ_id;
		this.mem_id = mem_id;
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

}