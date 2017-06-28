package com.orderdetail.model;

import java.util.List;

public interface OrderdetailDAO_interface {

	public void insert(OrderdetailVO orderdetailVO);
	public void update(OrderdetailVO orderdetailVO);
	public void delete(String ord_id, String prod_id);
	public OrderdetailVO findByPrimaryKey(String ord_id, String prod_id);
	public List<OrderdetailVO> getAll();

}