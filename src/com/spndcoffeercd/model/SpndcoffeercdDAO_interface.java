package com.spndcoffeercd.model;

import java.util.List;

public interface SpndcoffeercdDAO_interface {

	public void insert(SpndcoffeercdVO spndcoffeercdVO);
	public void update(SpndcoffeercdVO spndcoffeercdVO);
	public void delete(String rcd_id);
	public SpndcoffeercdVO findByPrimaryKey(String rcd_id);
	public List<SpndcoffeercdVO> getAll();

}