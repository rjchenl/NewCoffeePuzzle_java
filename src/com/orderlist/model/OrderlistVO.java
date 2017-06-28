package com.orderlist.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class OrderlistVO implements Serializable {

	private String ord_id;
	private String mem_id;
	private String store_id;
	private Integer ord_total;
	private Integer ord_pick;
	private String ord_add;
	private Integer ord_shipping;
	private Timestamp ord_time;
	private Integer score_buyer;
	private Integer score_seller;
	private Integer rept_buyer;
	private String rept_buyer_rsn;
	private Integer rept_buyer_rev;
	private Integer rept_seller;
	private String rept_seller_rsn;
	private Integer rept_seller_rev;
	private Integer ord_isreturn;
	private String return_rsn;

	public OrderlistVO(){}

	public OrderlistVO(String ord_id, String mem_id, String store_id, Integer ord_total, Integer ord_pick, String ord_add, Integer ord_shipping, Timestamp ord_time, Integer score_buyer, Integer score_seller, Integer rept_buyer, String rept_buyer_rsn, Integer rept_buyer_rev, Integer rept_seller, String rept_seller_rsn, Integer rept_seller_rev, Integer ord_isreturn, String return_rsn){
		this.ord_id = ord_id;
		this.mem_id = mem_id;
		this.store_id = store_id;
		this.ord_total = ord_total;
		this.ord_pick = ord_pick;
		this.ord_add = ord_add;
		this.ord_shipping = ord_shipping;
		this.ord_time = ord_time;
		this.score_buyer = score_buyer;
		this.score_seller = score_seller;
		this.rept_buyer = rept_buyer;
		this.rept_buyer_rsn = rept_buyer_rsn;
		this.rept_buyer_rev = rept_buyer_rev;
		this.rept_seller = rept_seller;
		this.rept_seller_rsn = rept_seller_rsn;
		this.rept_seller_rev = rept_seller_rev;
		this.ord_isreturn = ord_isreturn;
		this.return_rsn = return_rsn;
	}

	public String getOrd_id() {
		return ord_id;
	}

	public void setOrd_id(String ord_id) {
		this.ord_id = ord_id;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public Integer getOrd_total() {
		return ord_total;
	}

	public void setOrd_total(Integer ord_total) {
		this.ord_total = ord_total;
	}

	public Integer getOrd_pick() {
		return ord_pick;
	}

	public void setOrd_pick(Integer ord_pick) {
		this.ord_pick = ord_pick;
	}

	public String getOrd_add() {
		return ord_add;
	}

	public void setOrd_add(String ord_add) {
		this.ord_add = ord_add;
	}

	public Integer getOrd_shipping() {
		return ord_shipping;
	}

	public void setOrd_shipping(Integer ord_shipping) {
		this.ord_shipping = ord_shipping;
	}

	public Timestamp getOrd_time() {
		return ord_time;
	}

	public void setOrd_time(Timestamp ord_time) {
		this.ord_time = ord_time;
	}

	public Integer getScore_buyer() {
		return score_buyer;
	}

	public void setScore_buyer(Integer score_buyer) {
		this.score_buyer = score_buyer;
	}

	public Integer getScore_seller() {
		return score_seller;
	}

	public void setScore_seller(Integer score_seller) {
		this.score_seller = score_seller;
	}

	public Integer getRept_buyer() {
		return rept_buyer;
	}

	public void setRept_buyer(Integer rept_buyer) {
		this.rept_buyer = rept_buyer;
	}

	public String getRept_buyer_rsn() {
		return rept_buyer_rsn;
	}

	public void setRept_buyer_rsn(String rept_buyer_rsn) {
		this.rept_buyer_rsn = rept_buyer_rsn;
	}

	public Integer getRept_buyer_rev() {
		return rept_buyer_rev;
	}

	public void setRept_buyer_rev(Integer rept_buyer_rev) {
		this.rept_buyer_rev = rept_buyer_rev;
	}

	public Integer getRept_seller() {
		return rept_seller;
	}

	public void setRept_seller(Integer rept_seller) {
		this.rept_seller = rept_seller;
	}

	public String getRept_seller_rsn() {
		return rept_seller_rsn;
	}

	public void setRept_seller_rsn(String rept_seller_rsn) {
		this.rept_seller_rsn = rept_seller_rsn;
	}

	public Integer getRept_seller_rev() {
		return rept_seller_rev;
	}

	public void setRept_seller_rev(Integer rept_seller_rev) {
		this.rept_seller_rev = rept_seller_rev;
	}

	public Integer getOrd_isreturn() {
		return ord_isreturn;
	}

	public void setOrd_isreturn(Integer ord_isreturn) {
		this.ord_isreturn = ord_isreturn;
	}

	public String getReturn_rsn() {
		return return_rsn;
	}

	public void setReturn_rsn(String return_rsn) {
		this.return_rsn = return_rsn;
	}

}