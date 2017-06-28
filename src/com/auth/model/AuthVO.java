package com.auth.model;

import java.io.Serializable;

public class AuthVO implements Serializable {

	private String admin_id;
	private String feature_id;

	public AuthVO(){}

	public AuthVO(String admin_id, String feature_id){
		this.admin_id = admin_id;
		this.feature_id = feature_id;
	}

	public String getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}

	public String getFeature_id() {
		return feature_id;
	}

	public void setFeature_id(String feature_id) {
		this.feature_id = feature_id;
	}

}