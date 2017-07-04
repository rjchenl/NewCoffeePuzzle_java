package com.ming.orderlist.model;

import java.util.List;
import java.io.IOException;

public interface OrderlistDAO_interface {

	public void insert(OrderlistVO orderlistVO);
	public void update(OrderlistVO orderlistVO);
	public void delete(String ord_id);
	public OrderlistVO findByPrimaryKey(String ord_id) throws IOException ;
	public List<OrderlistVO> getAll() throws IOException ;

}