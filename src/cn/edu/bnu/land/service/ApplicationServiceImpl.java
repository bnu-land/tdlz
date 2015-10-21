package cn.edu.bnu.land.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.Application;
import cn.edu.bnu.land.model.ApplicationDAO;
import cn.edu.bnu.land.service.ApplicationService;
@Service
public class ApplicationServiceImpl implements ApplicationService{
	
	ApplicationDAO appDAO;
	
	public ApplicationDAO getAppDAO() {
		return appDAO;
	}
	@Autowired
	public void setAppDAO(ApplicationDAO appDAO) {
		this.appDAO = appDAO;
	}

	public void addApplication(Application application)
	{
		appDAO.save(application);
	}
	
	public void deleteApplication(Integer id)
	{
		appDAO.delete(id);
	}
	
	public void changeApplication(Application application)
	{
		appDAO.update(application);
	}
	
	public Application findApplication(Integer id)
	{
		return appDAO.getApplication(id);
	}
	
	public List<Application> showApplication()
	{
		return appDAO.findAll();
	}
	
	public List<Application> findAppByFarmer(Integer id)
	{
		return appDAO.findByFarmer(id);
	}
}
