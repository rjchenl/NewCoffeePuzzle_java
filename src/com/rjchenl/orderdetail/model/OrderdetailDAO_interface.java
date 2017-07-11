package com.rjchenl.orderdetail.model;

import java.util.List;


public interface OrderdetailDAO_interface {

	public void insert(OrderdetailVO orderdetailVO);
	public void update(OrderdetailVO orderdetailVO);
	public void delete(String ord_id, String prod_id);
	public OrderdetailVO findByPrimaryKey(String ord_id, String prod_id);
	public List<OrderdetailVO> getAll();
	
	 //同時新增部門與員工 (實務上並不常用, 但,可用在訂單主檔與明細檔一次新增成功)
    public void insert2 (OrderdetailVO orderdetailVO , java.sql.Connection con);

}