package cn.edu.bnu.land.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface WarmRulesDAO {
	public void save(WarmRules wr);
	public void update(WarmRules wr);
	public void delete(Integer id);
	public WarmRules get(Integer id);
	public List<WarmRules> findAll();
}
