package cn.edu.bnu.land.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.Rask;
import cn.edu.bnu.land.model.RaskDAO;
import cn.edu.bnu.land.service.RaskService;
@Service
public class RaskServiceImpl implements RaskService{

	RaskDAO rDAO;
	
	public RaskDAO getrDAO() {
		return rDAO;
	}
	@Autowired
	public void setrDAO(RaskDAO rDAO) {
		this.rDAO = rDAO;
	}

	public void addRask(Rask r) {
		// TODO Auto-generated method stub
		rDAO.save(r);
	}

	public void modifyRask(Rask r) {
		// TODO Auto-generated method stub
		rDAO.update(r);
	}

	public void removeRask(String id) {
		// TODO Auto-generated method stub
		rDAO.delete(id);
	}

	public List<Rask> showAll() {
		// TODO Auto-generated method stub
		return (List<Rask>)rDAO.findAll();
	}

	public Rask getById(String id) {
		// TODO Auto-generated method stub
		return (Rask)rDAO.get(id);
	}

}
