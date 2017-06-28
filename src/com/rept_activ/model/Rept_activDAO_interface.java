package com.rept_activ.model;

import java.util.List;

public interface Rept_activDAO_interface {

	public void insert(Rept_activVO rept_activVO);
	public void update(Rept_activVO rept_activVO);
	public void delete(String activ_id, String mem_id);
	public Rept_activVO findByPrimaryKey(String activ_id, String mem_id);
	public List<Rept_activVO> getAll();

}