package cn.edu.bnu.land.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.WarmRules;
import cn.edu.bnu.land.model.WarmRulesDAO;
import cn.edu.bnu.land.service.WarmRulesService;
@Service
public class WarmRulesServiceImpl implements WarmRulesService{

	WarmRulesDAO wrDAO;

	public WarmRulesDAO getWrDAO() {
		return wrDAO;
	}
	@Autowired
	public void setWrDAO(WarmRulesDAO wrDAO) {
		this.wrDAO = wrDAO;
	}

	public void addWarmRules(WarmRules wr) {
		// TODO Auto-generated method stub
		wrDAO.save(wr);
	}

	public void modifyRules(WarmRules wr) {
		// TODO Auto-generated method stub
		wrDAO.update(wr);
	}

	public void removeRules(Integer id) {
		// TODO Auto-generated method stub
		wrDAO.delete(id);
	}

	public List<WarmRules> showRules() {
		// TODO Auto-generated method stub
		return wrDAO.findAll();
	}

	public WarmRules getRule(Integer id) {
		// TODO Auto-generated method stub
		return wrDAO.get(id);
	}

}
