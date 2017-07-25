package com.ming.member.model;

import java.util.List;

public interface MemberDAO_interface {

	public void insert(MemberVO memberVO);
	public void update(MemberVO memberVO);
	public void delete(String mem_id);
	public MemberVO findByPrimaryKey(String mem_id) ;
	public List<MemberVO> getAll() ;
	public MemberVO findByMem(String mem_acct);
	public void getMem_Insert(String inser_memid, String inser_mem_psw, String inser_mem_name, String inser_mem_nanber,
			String inser_mem_mail, String inser_mem_add, Integer mem_points, byte[] mem_img);


}