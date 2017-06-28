package com.feature.model;

import java.io.Serializable;

public class FeatureVO implements Serializable {

	private String feature_id;
	private String feature_name;

	public FeatureVO(){}

	public FeatureVO(String feature_id, String feature_name){
		this.feature_id = feature_id;
		this.feature_name = feature_name;
	}

	public String getFeature_id() {
		return feature_id;
	}

	public void setFeature_id(String feature_id) {
		this.feature_id = feature_id;
	}

	public String getFeature_name() {
		return feature_name;
	}

	public void setFeature_name(String feature_name) {
		this.feature_name = feature_name;
	}

}