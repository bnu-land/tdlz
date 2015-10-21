package cn.edu.bnu.land.service;

import java.util.Date;
import java.util.List;

import cn.edu.bnu.land.model.OwnersUnit;

public interface OwnersUnitService {
	public void addOwnersUnit(OwnersUnit OwnersUnit);
	public void deleteOwnersUnit(Integer id);
	public void updateOwnersUnit(OwnersUnit OwnersUnit);
	public OwnersUnit getByID(Integer id);
	public List<OwnersUnit> showOwnersUnit();
	
}