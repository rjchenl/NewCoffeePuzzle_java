package com.rjchenl.fav_store.model;

import java.util.List;

public interface Fav_storeDAO_interface {

	public void insert(Fav_storeVO fav_storeVO);
	public void delete(String mem_id, String store_id);
	public Fav_storeVO findByPrimaryKey(String mem_id, String store_id);
	public List<Fav_storeVO> getAll();
	public List<Fav_storeVO> getCombinationString ();
	public byte[] getImage(String store_id);

}