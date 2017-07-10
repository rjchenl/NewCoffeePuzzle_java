package com.ming.spndcoffeelist.model;

import java.util.List;

public interface SpndcoffeelistDAO_interface {

	public void insert(SpndcoffeelistVO spndcoffeelistVO);
	public void update(SpndcoffeelistVO spndcoffeelistVO);
	public void delete(String list_id);
	public SpndcoffeelistVO findByPrimaryKey(String list_id) ;
	public List<SpndcoffeelistVO> getAll() ;
	public List<SpndcoffeelistVO> getStore(String store_id);
	public List<SpndcoffeelistVO> getUpdate(String store_id,String list_id,Integer list_left);
}