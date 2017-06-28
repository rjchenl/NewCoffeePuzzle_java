package com.report.model;

import java.util.List;

public interface ReportDAO_interface {

	public void insert(ReportVO reportVO);
	public void update(ReportVO reportVO);
	public void delete(String mem_id, String rnr_id);
	public ReportVO findByPrimaryKey(String mem_id, String rnr_id);
	public List<ReportVO> getAll();

}