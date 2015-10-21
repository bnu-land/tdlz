package cn.edu.bnu.land.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.UserInformation;
import cn.edu.bnu.land.model.UserInformationDAO;
import cn.edu.bnu.land.service.UserInformationService;
@Service
public class UserInformationServiceImpl implements
UserInformationService{
	
	UserInformationDAO userInformationDAO;
	
	

	public UserInformationDAO getUserInformationDAO() {
		return userInformationDAO;
	}
	@Autowired
	public void setUserInformationDAO(UserInformationDAO userInformationDAO) {
		this.userInformationDAO = userInformationDAO;
	}

	public UserInformation getByID(Integer id)
	{
		return userInformationDAO.get(id);
	}
	
	public void addUserInformation(UserInformation UserInformation)
	{
		userInformationDAO.save(UserInformation);
	}
	
	public void deleteUserInformation(Integer id)
	{
		userInformationDAO.delete(id);
	}
	
	public List<UserInformation> showUserInformation()
	{
		return userInformationDAO.findAll();
	}
	
	public List<UserInformation> filterUserInformation(Date date)
	{
		return userInformationDAO.findByDate(date);
	}
}

