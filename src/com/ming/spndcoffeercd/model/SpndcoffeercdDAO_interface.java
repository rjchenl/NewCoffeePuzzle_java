package com.ming.spndcoffeercd.model;

import java.sql.Timestamp;
import java.util.List;

public interface SpndcoffeercdDAO_interface {

	public void insert(SpndcoffeercdVO spndcoffeercdVO);
	public void update(SpndcoffeercdVO spndcoffeercdVO);
	public void delete(String rcd_id);
	public SpndcoffeercdVO findByPrimaryKey(String rcd_id);
	public List<SpndcoffeercdVO> getAll();
	public void getCDInser(String list_id, Integer single_amt, Timestamp rcd_date);

}