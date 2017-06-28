package com.member.model;

import java.util.List;
import java.util.Set;
import com.spndcoffeelist.model.SpndcoffeelistVO;
import com.rate_n_rev.model.Rate_n_revVO;
import com.report.model.ReportVO;
import com.orderlist.model.OrderlistVO;
import com.msg.model.MsgVO;
import com.reply.model.ReplyVO;
import com.activity.model.ActivityVO;
import com.participant.model.ParticipantVO;
import com.rept_activ.model.Rept_activVO;
import com.fav_store.model.Fav_storeVO;
import com.photo_store.model.Photo_storeVO;
import com.rept_store.model.Rept_storeVO;

public interface MemberDAO_interface {

	public void insert(MemberVO memberVO);
	public void update(MemberVO memberVO);
	public void delete(String mem_id);
	public MemberVO findByPrimaryKey(String mem_id);
	public List<MemberVO> getAll();
	public Set<SpndcoffeelistVO> getSpndcoffeelistsByMem_id(String mem_id);
	public Set<Rate_n_revVO> getRate_n_revsByMem_id(String mem_id);
	public Set<ReportVO> getReportsByMem_id(String mem_id);
	public Set<OrderlistVO> getOrderlistsByMem_id(String mem_id);
	public Set<MsgVO> getMsgsByMem_id(String mem_id);
	public Set<ReplyVO> getReplysByMem_id(String mem_id);
	public Set<ActivityVO> getActivitysByMem_id(String mem_id);
	public Set<ParticipantVO> getParticipantsByMem_id(String mem_id);
	public Set<Rept_activVO> getRept_activsByMem_id(String mem_id);
	public Set<Fav_storeVO> getFav_storesByMem_id(String mem_id);
	public Set<Photo_storeVO> getPhoto_storesByMem_id(String mem_id);
	public Set<Rept_storeVO> getRept_storesByMem_id(String mem_id);

}