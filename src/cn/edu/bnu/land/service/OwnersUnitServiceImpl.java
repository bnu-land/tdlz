package cn.edu.bnu.land.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.FarmersInformation;
import cn.edu.bnu.land.model.OwnersUnit;
import cn.edu.bnu.land.model.OwnersUnitDAO;
import cn.edu.bnu.land.service.OwnersUnitService;
@Service
public class OwnersUnitServiceImpl implements
OwnersUnitService{
	
	OwnersUnitDAO ownersUnitDAO;
	
	

	public OwnersUnitDAO getOwnersUnitDAO() {
		return ownersUnitDAO;
	}
	@Autowired
	public void setOwnersUnitDAO(OwnersUnitDAO OwnersUnitDAO) {
		this.ownersUnitDAO = OwnersUnitDAO;
	}

	public OwnersUnit getByID(Integer id)
	{
		return ownersUnitDAO.get(id);
	}
	
	public void updateOwnersUnit(OwnersUnit OwnersUnit)
	{
		ownersUnitDAO.update(OwnersUnit);
	}
	
	public void addOwnersUnit(OwnersUnit OwnersUnit)
	{
		ownersUnitDAO.save(OwnersUnit);
	}
	
	public void deleteOwnersUnit(Integer id)
	{
		ownersUnitDAO.delete(id);
	}
	
	public List<OwnersUnit> showOwnersUnit()
	{
		return ownersUnitDAO.findAll();
	}
}

