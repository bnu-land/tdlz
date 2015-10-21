package cn.edu.bnu.land.model;
import java.util.List;


public interface FarmersInformationDAO {
	public void save(FarmersInformation FarmersInformation);
	public void update(FarmersInformation FarmersInformation);
	public void delete(Integer id);
	public FarmersInformation get(Integer id);
	public List<FarmersInformation> findAll();
	
}
