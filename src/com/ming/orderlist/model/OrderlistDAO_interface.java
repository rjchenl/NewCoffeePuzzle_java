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
	public List<OrderlistVO> getOrdelist_1(String store_id);
	public List<OrderlistVO> getOrdelist_2(String store_id);
	public List<OrderlistVO> getOrdelist_3(String store_id);
	public List<OrderlistVO> getOrdelist_4(String store_id);
	public void getOrdelist_Update(String store_id, String ord_id);
	public void getOrdelist_NO_Update(String store_id, String ord_id);
	public void getOrdelist_GO_Update(String store_id, String ord_id_2);
	public void getDeliveryUpdate(String store_id, String ord_id);

}