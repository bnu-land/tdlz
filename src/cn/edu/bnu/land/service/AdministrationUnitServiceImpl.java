package cn.edu.bnu.land.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.AdministrationUnit;
import cn.edu.bnu.land.model.AdministrationUnitDAO;
import cn.edu.bnu.land.service.AdministrationUnitService;
@Service
public class AdministrationUnitServiceImpl implements
AdministrationUnitService{
	
	AdministrationUnitDAO administrationUnitDAO;
	
	

	public AdministrationUnitDAO getAdministrationUnitDAO() {
		return administrationUnitDAO;
	}
	@Autowired
	public void setAdministrationUnitDAO(AdministrationUnitDAO AdministrationUnitDAO) {
		this.administrationUnitDAO = AdministrationUnitDAO;
	}

	public AdministrationUnit getByID(Integer id)
	{
		return administrationUnitDAO.get(id);
	}
	
	public void updateAdministrationUnit(AdministrationUnit AdministrationUnit)
	{
		administrationUnitDAO.update(AdministrationUnit);
	}
	
	public void addAdministrationUnit(AdministrationUnit AdministrationUnit)
	{
		administrationUnitDAO.save(AdministrationUnit);
	}
	
	public void deleteAdministrationUnit(Integer id)
	{
		administrationUnitDAO.delete(id);
	}
	
	public List<AdministrationUnit> showAdministrationUnit()
	{
		return administrationUnitDAO.findAll();
	}
	
	
}


