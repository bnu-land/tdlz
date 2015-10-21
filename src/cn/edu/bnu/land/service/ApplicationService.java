package cn.edu.bnu.land.service;

import java.util.Date;
import java.util.List;

import cn.edu.bnu.land.model.Application;


public interface ApplicationService {
	public void addApplication(Application application);
	public void deleteApplication(Integer id);
	public void changeApplication(Application application);	
	public Application findApplication(Integer id);
	public List<Application> findAppByFarmer(Integer id);
	public List<Application> showApplication();

}
