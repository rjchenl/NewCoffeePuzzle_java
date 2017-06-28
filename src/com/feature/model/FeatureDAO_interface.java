package com.feature.model;

import java.util.List;
import java.util.Set;
import com.auth.model.AuthVO;

public interface FeatureDAO_interface {

	public void insert(FeatureVO featureVO);
	public void update(FeatureVO featureVO);
	public void delete(String feature_id);
	public FeatureVO findByPrimaryKey(String feature_id);
	public List<FeatureVO> getAll();
	public Set<AuthVO> getAuthsByFeature_id(String feature_id);

}