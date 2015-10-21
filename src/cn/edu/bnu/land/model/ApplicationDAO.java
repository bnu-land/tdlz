package cn.edu.bnu.land.model;

import java.util.Date;
import java.util.List;

public interface ApplicationDAO {
	public void save(Application application);
	public void update(Application application);
	public void delete(Integer id);
	public Application getApplication(Integer id);
	public List<Application> findByFarmer(Integer id);
	public List<Application> findAll();
}
