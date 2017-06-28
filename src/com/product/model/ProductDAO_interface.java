package com.product.model;

import java.util.List;
import java.util.Set;
import com.orderdetail.model.OrderdetailVO;
import com.msg.model.MsgVO;

public interface ProductDAO_interface {

	public void insert(ProductVO productVO);
	public void update(ProductVO productVO);
	public void delete(String prod_id);
	public ProductVO findByPrimaryKey(String prod_id);
	public List<ProductVO> getAll();
	public Set<OrderdetailVO> getOrderdetailsByProd_id(String prod_id);
	public Set<MsgVO> getMsgsByProd_id(String prod_id);

}