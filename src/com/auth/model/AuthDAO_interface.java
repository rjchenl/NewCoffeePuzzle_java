package com.auth.model;

import java.util.List;

public interface AuthDAO_interface {

	public void insert(AuthVO authVO);
	public void delete(String admin_id, String feature_id);
	public AuthVO findByPrimaryKey(String admin_id, String feature_id);
	public List<AuthVO> getAll();

}