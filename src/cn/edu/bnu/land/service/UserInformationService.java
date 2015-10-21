package cn.edu.bnu.land.service;

import java.util.Date;
import java.util.List;

import cn.edu.bnu.land.model.UserInformation;

public interface UserInformationService {
	public void addUserInformation(UserInformation UserInformation);
	public void deleteUserInformation(Integer id);
	public UserInformation getByID(Integer id);
	public List<UserInformation> showUserInformation();
	public List<UserInformation> filterUserInformation(Date date);
}