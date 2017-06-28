package com.activity.model;

import java.util.List;
import java.util.Set;
import com.participant.model.ParticipantVO;
import com.rept_activ.model.Rept_activVO;

public interface ActivityDAO_interface {

	public void insert(ActivityVO activityVO);
	public void update(ActivityVO activityVO);
	public void delete(String activ_id);
	public ActivityVO findByPrimaryKey(String activ_id);
	public List<ActivityVO> getAll();
	public Set<ParticipantVO> getParticipantsByActiv_id(String activ_id);
	public Set<Rept_activVO> getRept_activsByActiv_id(String activ_id);

}