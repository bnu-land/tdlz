package cn.edu.bnu.land.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.Projection;
import cn.edu.bnu.land.model.ProjectionDAO;
import cn.edu.bnu.land.service.ProjectionService;
@Service
public class ProjectionServiceImpl implements ProjectionService{

	ProjectionDAO pDAO;
	
	public ProjectionDAO getpDAO() {
		return pDAO;
	}
	@Autowired
	public void setpDAO(ProjectionDAO pDAO) {
		this.pDAO = pDAO;
	}

	public void addProjection(Projection p) {
		// TODO Auto-generated method stub
		pDAO.save(p);
	}

	public void modifyProjection(Projection p) {
		// TODO Auto-generated method stub
		pDAO.update(p);
	}

	public List<Projection> showAll() {
		// TODO Auto-generated method stub
		return pDAO.findAll();
	}

	public List<Projection> showByNumber(String number) {
		// TODO Auto-generated method stub
		return pDAO.findByNumber(number);
	}

}
