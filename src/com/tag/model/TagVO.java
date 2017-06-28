package com.tag.model;

import java.io.Serializable;

public class TagVO implements Serializable {

	private String tag_id;
	private String tag_content;

	public TagVO(){}

	public TagVO(String tag_id, String tag_content){
		this.tag_id = tag_id;
		this.tag_content = tag_content;
	}

	public String getTag_id() {
		return tag_id;
	}

	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}

	public String getTag_content() {
		return tag_content;
	}

	public void setTag_content(String tag_content) {
		this.tag_content = tag_content;
	}

}