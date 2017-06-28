package com.store_tag.model;

import java.util.List;

public interface Store_tagDAO_interface {

	public void insert(Store_tagVO store_tagVO);
	public void update(Store_tagVO store_tagVO);
	public void delete(String store_id, String tag_id);
	public Store_tagVO findByPrimaryKey(String store_id, String tag_id);
	public List<Store_tagVO> getAll();

}