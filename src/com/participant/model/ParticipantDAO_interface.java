package com.participant.model;

import java.util.List;

public interface ParticipantDAO_interface {

	public void insert(ParticipantVO participantVO);
	public void delete(String activ_id, String mem_id);
	public ParticipantVO findByPrimaryKey(String activ_id, String mem_id);
	public List<ParticipantVO> getAll();

}