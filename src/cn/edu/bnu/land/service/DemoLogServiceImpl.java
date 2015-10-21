package cn.edu.bnu.land.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.DemoLogDAO;
import cn.edu.bnu.land.model.DemolitionLog;
import cn.edu.bnu.land.service.DemoLogService;
@Service
public class DemoLogServiceImpl implements DemoLogService{
	
	DemoLogDAO logDAO;
	
	
	public DemoLogDAO getLogDAO() {
		return logDAO;
	}
	@Autowired
	public void setLogDAO(DemoLogDAO logDAO) {
		this.logDAO = logDAO;
	}

	public void addLog(DemolitionLog log)
	{
		logDAO.save(log);
	}
	
	public void updatePhoto(DemolitionLog log)
	{
		logDAO.update(log);
	}
	
	public List<DemolitionLog> showAll()
	{
		return logDAO.findAll();
	}
	
	public boolean findByDate(Date date, String sfz)
	{
		if (logDAO.findByDate(date).size() == 0) {
			return false;
		}else {
			List<DemolitionLog> d = logDAO.findByDate(date);
			for (DemolitionLog dtemp : d) {
				if (dtemp.getCp().getDYNHSFZ().equals(sfz)) {
					return true;
				}
			}
			
			return false;
		}
		
	}
}
