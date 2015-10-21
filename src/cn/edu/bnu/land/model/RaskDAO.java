package cn.edu.bnu.land.model;

import java.util.List;

public interface RaskDAO {
	public void save(Rask r);
	public void delete(String id);
	public void update(Rask r);
	public List<Rask> findAll();
	public Rask get(String id);
}
