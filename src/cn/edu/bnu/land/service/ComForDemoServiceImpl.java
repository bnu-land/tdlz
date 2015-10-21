package cn.edu.bnu.land.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.ComForDemoDAO;
import cn.edu.bnu.land.model.ComForDemolition;
import cn.edu.bnu.land.model.FarmersInformation;
import cn.edu.bnu.land.service.ComForDemoService;
@Service
public class ComForDemoServiceImpl implements ComForDemoService{

	ComForDemoDAO cfdDAO;

	public ComForDemoDAO getCfdDAO() {
		return cfdDAO;
	}
	@Autowired
	public void setCfdDAO(ComForDemoDAO cfdDAO) {
		this.cfdDAO = cfdDAO;
	}
	
	public void addComForDemolition(ComForDemolition cfd)
	{
		cfdDAO.save(cfd);
	}
	
	public void changeComForDemolition(ComForDemolition cfd)
	{
		cfdDAO.update(cfd);
	}
	
	public List<ComForDemolition> showAll()
	{
		return cfdDAO.findAll();
	}
	
	public List<ComForDemolition> showByFarmer(Integer id)
	{
		return cfdDAO.getByFarmerID(id);
	}
	
	public ComForDemolition getByID(Integer id)
	{
		return cfdDAO.get(id);
	}

	public List<ComForDemolition> showByProject(String id) {
		// TODO Auto-generated method stub
		return (List<ComForDemolition>)cfdDAO.getByProject(id);
	}
	

}
