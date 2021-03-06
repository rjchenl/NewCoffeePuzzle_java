package com.rjchenl.orderlist.model;

import java.util.List;
import java.util.Set;

import com.rjchenl.orderdetail.model.OrderdetailVO;


public interface OrderlistDAO_interface {

	public void insert(OrderlistVO orderlistVO);
	public void update(OrderlistVO orderlistVO);
	public void delete(String ord_id);
	public OrderlistVO findByPrimaryKey(String ord_id);
	public List<OrderlistVO> getAll();
	public Set<OrderdetailVO> getOrderdetailsByOrd_id(String ord_id);
	
	public List<OrderlistVO> getMyOrderList(String mem_id);
	
	public void insertWithOrderDetail(OrderlistVO orerlistvo,List<OrderdetailVO> list);
	
	
}