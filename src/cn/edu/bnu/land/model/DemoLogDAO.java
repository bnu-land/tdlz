package cn.edu.bnu.land.model;

import java.util.Date;
import java.util.List;

public interface DemoLogDAO {
	public void save(DemolitionLog log);
	public void update(DemolitionLog log);
	public DemolitionLog get(Integer id);
	public void delete(Integer id);
	public List<DemolitionLog> findByDate(Date date);
	public List<DemolitionLog> findAll();
	public List<DemolitionLog> findByProjectID(String projectID);
	
}
