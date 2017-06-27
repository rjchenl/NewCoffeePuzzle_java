package com.rjchenl.server.activies;

import java.util.List;

public interface ActivityDAO_interface {
	public void insert(ActivityVO actVO);
	public void update(ActivityVO actVO);
	public void delete(String activ_id);
	public ActivityVO findByPrimaryKey(String activ_id);
	public List<ActivityVO> getAll();
	public byte[] getImage(String activ_id);

}
