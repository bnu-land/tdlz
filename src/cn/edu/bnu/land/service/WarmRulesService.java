package cn.edu.bnu.land.service;

import java.util.List;

import cn.edu.bnu.land.model.WarmRules;

public interface WarmRulesService {
	public void addWarmRules(WarmRules wr);
	public void modifyRules(WarmRules wr);
	public void removeRules(Integer id);
	public WarmRules getRule(Integer id);
	public List<WarmRules> showRules();
}
