package com.admin.model;

import java.util.List;
import java.util.Set;
import com.auth.model.AuthVO;

public interface AdminDAO_interface {

	public void insert(AdminVO adminVO);
	public void update(AdminVO adminVO);
	public void delete(String admin_id);
	public AdminVO findByPrimaryKey(String admin_id);
	public List<AdminVO> getAll();
	public Set<AuthVO> getAuthsByAdmin_id(String admin_id);

}