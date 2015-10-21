package cn.edu.bnu.land.service;

import java.util.List;

import cn.edu.bnu.land.model.SpatialData;

public interface SpatialDataService {
	public void createSpatialData(SpatialData sd);
	public void modifySpatialData(SpatialData sd);
	public List<SpatialData> show();
}
