package com.rjchenl.product.model;

import java.util.List;
import java.util.Set;

import com.msg.model.MsgVO;
import com.rjchenl.orderdetail.model.OrderdetailVO;

public interface ProductDAO_interface {

	public void insert(ProductVO productVO);
	public void update(ProductVO productVO);
	public void delete(String prod_id);
	public ProductVO findByPrimaryKey(String prod_id);
	public List<ProductVO> getAll();
	public Set<OrderdetailVO> getOrderdetailsByProd_id(String prod_id);
	public Set<MsgVO> getMsgsByProd_id(String prod_id);
	public List<ProductVO> getStoreProductByStoreName(String store_name);

}