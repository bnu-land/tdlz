package cn.edu.bnu.land.model;

import java.util.Date;
import java.util.List;


public interface UserInformationDAO {
	public void save(UserInformation UserInformation);
	public void update(UserInformation UserInformation);
	public void delete(Integer id);
	public UserInformation get(Integer id);
	public List<UserInformation> findAll();
	public List<UserInformation> findByDate(Date date);
}

