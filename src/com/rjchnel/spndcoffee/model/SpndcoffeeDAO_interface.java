package com.rjchnel.spndcoffee.model;

import java.util.List;
import java.util.Set;

import com.rjchenl.spndcoffeelist.model.SpndcoffeelistVO;

public interface SpndcoffeeDAO_interface {

	public void insert(SpndcoffeeVO spndcoffeeVO);
	public void update(SpndcoffeeVO spndcoffeeVO);
	public void delete(String spnd_id);
	public SpndcoffeeVO findByPrimaryKey(String spnd_id);
	public List<SpndcoffeeVO> getAll();
	public Set<SpndcoffeelistVO> getSpndcoffeelistsBySpnd_id(String spnd_id);
	public List<SpndcoffeeVO> getAll_store_name();

}