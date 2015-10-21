package cn.edu.bnu.land.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.SpatialData;
import cn.edu.bnu.land.model.SpatialDataDAO;
import cn.edu.bnu.land.service.SpatialDataService;
@Service
public class SpatialDataServiceImpl implements SpatialDataService{

	SpatialDataDAO sdDAO;
	
	
	public SpatialDataDAO getSdDAO() {
		return sdDAO;
	}
	@Autowired
	public void setSdDAO(SpatialDataDAO sdDAO) {
		this.sdDAO = sdDAO;
	}

	public void createSpatialData(SpatialData sd) {
		// TODO Auto-generated method stub
		sdDAO.save(sd);
	}

	public void modifySpatialData(SpatialData sd) {
		// TODO Auto-generated method stub
		sdDAO.update(sd);
	}

	public List<SpatialData> show() {
		// TODO Auto-generated method stub
		return sdDAO.findAll();
	}
	
}
