package cn.edu.bnu.land.model;

import java.util.List;

public interface ProjectionDAO {
	public void save(Projection p);
	public List<Projection> findAll();
	public void update(Projection p);
	public List<Projection> findByNumber(String number);
	
}
