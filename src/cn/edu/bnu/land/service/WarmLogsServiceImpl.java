package cn.edu.bnu.land.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.WarmLogs;
import cn.edu.bnu.land.model.WarmLogsDAO;
import cn.edu.bnu.land.service.WarmLogsService;
@Service
public class WarmLogsServiceImpl implements WarmLogsService{

	WarmLogsDAO wlsDAO;
	
	
	
	public WarmLogsDAO getWlsDAO() {
		return wlsDAO;
	}
	@Autowired
	public void setWlsDAO(WarmLogsDAO wlsDAO) {
		this.wlsDAO = wlsDAO;
	}

	public void addWarmLogs(WarmLogs wls) {
		// TODO Auto-generated method stub
		wlsDAO.save(wls);
	}

	public void modifyWarmLogs(WarmLogs wls) {
		// TODO Auto-generated method stub
		wlsDAO.update(wls);
	}

	public WarmLogs getByID(Integer id) {
		// TODO Auto-generated method stub
		return wlsDAO.get(id);
	}

	public void removeWarmLogs(Integer id) {
		// TODO Auto-generated method stub
		wlsDAO.delete(id);
	}

	public List<WarmLogs> showAll() {
		// TODO Auto-generated method stub
		return wlsDAO.showAll();
	}

}
