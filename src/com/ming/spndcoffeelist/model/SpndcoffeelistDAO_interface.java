package com.ming.spndcoffeelist.model;

import java.sql.Timestamp;
import java.util.List;

public interface SpndcoffeelistDAO_interface {

	public void insert(SpndcoffeelistVO spndcoffeelistVO);
	public void update(SpndcoffeelistVO spndcoffeelistVO);
	public void delete(String list_id);
	public SpndcoffeelistVO findByPrimaryKey(String list_id) ;
	public List<SpndcoffeelistVO> getAll() ;
	public List<SpndcoffeelistVO> getStore(String store_id);
	public List<SpndcoffeelistVO> getUpdate(String store_id,String list_id,Integer list_left);
	public void getInsert(String store_id, String mem_id, String spnd_id, String spnd_prod, Integer list_amt,
			Integer list_left, Timestamp list_date);
	public SpndcoffeelistVO getDelivery_UpdateALL(String store_id, String list_id);
}