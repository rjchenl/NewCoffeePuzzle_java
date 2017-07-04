package com.ming.spndcoffeelist.model;

import java.util.List;

public interface SpndcoffeelistDAO_interface {

	public void insert(SpndcoffeelistVO spndcoffeelistVO);
	public void update(SpndcoffeelistVO spndcoffeelistVO);
	public void delete(String list_id);
	public SpndcoffeelistVO findByPrimaryKey(String list_id) ;
	public List<SpndcoffeelistVO> getAll() ;

}