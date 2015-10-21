package cn.edu.bnu.land.model;
import java.util.List;


public interface AdministrationUnitDAO {
	public void save(AdministrationUnit AdministrationUnit);
	public void update(AdministrationUnit AdministrationUnit);
	public void delete(Integer id);
	public AdministrationUnit get(Integer id);
	public List<AdministrationUnit> findAll();
	
}
