package com.ming.orderlist.model;

import java.util.List;
import java.util.Set;
import com.ming.orderdetail_model.OrderdetailVO;

public interface OrderlistDAO_interface {

	public void insert(OrderlistVO orderlistVO);
	public void update(OrderlistVO orderlistVO);
	public void delete(String ord_id);
	public OrderlistVO findByPrimaryKey(String ord_id);
	public List<OrderlistVO> getAll();
	public Set<OrderdetailVO> getOrderdetailsByOrd_id(String ord_id);
	public List<OrderlistVO> getOrdelist(String store_id);

}