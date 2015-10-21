package cn.edu.bnu.land.model;

import java.util.List;

public interface ComForDemoDAO {
	public void update(ComForDemolition cfd);
	public void save(ComForDemolition cfd);
	public void delete(Integer id);
	public ComForDemolition get(Integer id);
	public List<ComForDemolition> getByFarmerID(Integer id);
	public List<ComForDemolition> findAll();
	public List<ComForDemolition> getByProject(String id);
}
