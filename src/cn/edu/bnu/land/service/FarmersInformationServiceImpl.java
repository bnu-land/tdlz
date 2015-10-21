package cn.edu.bnu.land.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.FarmersInformation;
import cn.edu.bnu.land.model.FarmersInformationDAO;
import cn.edu.bnu.land.service.FarmersInformationService;
@Service
public class FarmersInformationServiceImpl implements
FarmersInformationService{
	
	FarmersInformationDAO farmersInformationDAO;
	
	

	public FarmersInformationDAO getFarmersInformationDAO() {
		return farmersInformationDAO;
	}
	@Autowired
	public void setFarmersInformationDAO(FarmersInformationDAO FarmersInformation) {
		this.farmersInformationDAO = FarmersInformation;
	}

	public FarmersInformation getByID(Integer id)
	{
		return farmersInformationDAO.get(id);
	}
	
	public void updateFarmersInformation(FarmersInformation FarmersInformation)
	{
		farmersInformationDAO.update(FarmersInformation);
	}
	
	public void addFarmersInformation(FarmersInformation FarmersInformation)
	{
		farmersInformationDAO.save(FarmersInformation);
	}
	
	public void deleteFarmersInformation(Integer id)
	{
		farmersInformationDAO.delete(id);
	}
	
	public List<FarmersInformation> showFarmersInformation()
	{
		return farmersInformationDAO.findAll();
	}

}


