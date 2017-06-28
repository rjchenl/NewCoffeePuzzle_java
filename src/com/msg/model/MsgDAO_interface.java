package com.msg.model;

import java.util.List;
import java.util.Set;
import com.reply.model.ReplyVO;

public interface MsgDAO_interface {

	public void insert(MsgVO msgVO);
	public void update(MsgVO msgVO);
	public void delete(String msg_id);
	public MsgVO findByPrimaryKey(String msg_id);
	public List<MsgVO> getAll();
	public Set<ReplyVO> getReplysByMsg_id(String msg_id);

}