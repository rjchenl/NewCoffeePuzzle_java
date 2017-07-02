package com.rjchen.store.model;

import java.util.List;
import java.util.Set;
import com.spndcoffee.model.SpndcoffeeVO;
import com.rate_n_rev.model.Rate_n_revVO;
import com.news.model.NewsVO;
import com.store_tag.model.Store_tagVO;
import com.product.model.ProductVO;
import com.orderlist.model.OrderlistVO;
import com.reply.model.ReplyVO;
import com.activity.model.ActivityVO;
import com.photo_store.model.Photo_storeVO;
import com.rept_store.model.Rept_storeVO;
import com.rjchenl.fav_store.model.Fav_storeVO;
import com.rjchenl.spndcoffeelist.model.SpndcoffeelistVO;

public interface StoreDAO_interface {

	public void insert(StoreVO storeVO);
	public void update(StoreVO storeVO);
	public void delete(String store_id);
	public StoreVO findByPrimaryKey(String store_id);
	public List<StoreVO> getAll();
	public Set<SpndcoffeeVO> getSpndcoffeesByStore_id(String store_id);
	public Set<SpndcoffeelistVO> getSpndcoffeelistsByStore_id(String store_id);
	public Set<Rate_n_revVO> getRate_n_revsByStore_id(String store_id);
	public Set<NewsVO> getNewssByStore_id(String store_id);
	public Set<Store_tagVO> getStore_tagsByStore_id(String store_id);
	public Set<ProductVO> getProductsByStore_id(String store_id);
	public Set<OrderlistVO> getOrderlistsByStore_id(String store_id);
	public Set<ReplyVO> getReplysByStore_id(String store_id);
	public Set<ActivityVO> getActivitysByStore_id(String store_id);
	public Set<Fav_storeVO> getFav_storesByStore_id(String store_id);
	public Set<Photo_storeVO> getPhoto_storesByStore_id(String store_id);
	public Set<Rept_storeVO> getRept_storesByStore_id(String store_id);
	public byte[] getImage(String store_id);
}