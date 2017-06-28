package com.spndcoffeelist.model;

import java.util.List;
import java.util.Set;
import com.spndcoffeercd.model.SpndcoffeercdVO;

public interface SpndcoffeelistDAO_interface {

	public void insert(SpndcoffeelistVO spndcoffeelistVO);
	public void update(SpndcoffeelistVO spndcoffeelistVO);
	public void delete(String list_id);
	public SpndcoffeelistVO findByPrimaryKey(String list_id);
	public List<SpndcoffeelistVO> getAll();
	public Set<SpndcoffeercdVO> getSpndcoffeercdsByList_id(String list_id);

}