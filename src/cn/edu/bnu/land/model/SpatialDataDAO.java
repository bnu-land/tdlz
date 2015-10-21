package cn.edu.bnu.land.model;

import java.util.List;

public interface SpatialDataDAO {
	public void save(SpatialData sd);
	public void update(SpatialData sd);
	public List<SpatialData> findAll();
}
