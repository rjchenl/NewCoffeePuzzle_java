package com.ming.orderdetail_model;

import java.util.List;

public interface OrderdetailDAO_interface {

	public void insert(OrderdetailVO orderdetailVO);
	public void update(OrderdetailVO orderdetailVO);
	public void delete(String ord_id, String prod_id);
	public OrderdetailVO findByPrimaryKey(String ord_id, String prod_id);
	public List<OrderdetailVO> getAll();
	public List<OrderdetailVO> getOrderdetail(String store_id,String ord_id);
	public List<OrderdetailVO> getOrderdetail_2(String store_id, String ord_id_2);
	public List<OrderdetailVO> getOrderdetail_3(String store_id, String ord_id_3);
	public List<OrderdetailVO> getOrderdetail_4(String store_id, String ord_id_4);
	public OrderdetailVO getDelivery_ALL(String ord_id);

}