package cn.edu.bnu.land.model;

import java.util.Date;
import java.util.List;


public interface OwnersUnitDAO {
	public void save(OwnersUnit OwnersUnit);
	public void update(OwnersUnit OwnersUnit);
	public void delete(Integer id);
	public OwnersUnit get(Integer id);
	public List<OwnersUnit> findAll();
}

