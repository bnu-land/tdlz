package cn.edu.bnu.land.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.UnfindCard;
import cn.edu.bnu.land.model.UnfindCardDAO;
import cn.edu.bnu.land.service.UnfindCardService;

@Service
public class UnfindCardServiceImpl implements UnfindCardService{

	UnfindCardDAO ucDAO;
	
	

	public UnfindCardDAO getUcDAO() {
		return ucDAO;
	}
	@Autowired
	public void setUcDAO(UnfindCardDAO ucDAO) {
		this.ucDAO = ucDAO;
	}

	public void addUnfindCard(UnfindCard c) {
		// TODO Auto-generated method stub
		ucDAO.save(c);
	}

	public void removeUnfindCard(Integer id) {
		// TODO Auto-generated method stub
		ucDAO.delete(id);
	}

	public void modifyUnfindCard(UnfindCard c) {
		// TODO Auto-generated method stub
		ucDAO.update(c);
	}

	public List<UnfindCard> showAll() {
		// TODO Auto-generated method stub
		return (List<UnfindCard>)ucDAO.findAll();
	}

	public List<UnfindCard> showByProject(String id) {
		// TODO Auto-generated method stub
		return (List<UnfindCard>)ucDAO.findByProject(id);
	}

	public UnfindCard getByID(Integer id) {
		// TODO Auto-generated method stub
		return (UnfindCard)ucDAO.get(id);
	}

}
