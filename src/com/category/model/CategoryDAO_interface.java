package com.category.model;

import java.util.List;
import java.util.Set;
import com.product.model.ProductVO;

public interface CategoryDAO_interface {

	public void insert(CategoryVO categoryVO);
	public void update(CategoryVO categoryVO);
	public void delete(String cate_id);
	public CategoryVO findByPrimaryKey(String cate_id);
	public List<CategoryVO> getAll();
	public Set<ProductVO> getProductsByCate_id(String cate_id);

}