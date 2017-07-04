package com.ming.store.model;

import java.util.List;

public interface StoreDAO_interface {

	public void insert(StoreVO storeVO);
	public void update(StoreVO storeVO);
	public void delete(String store_id);
	public StoreVO findByPrimaryKey(String store_id) ;
	public List<StoreVO> getAll() ;
	public StoreVO findByStore(String store_acct);

}