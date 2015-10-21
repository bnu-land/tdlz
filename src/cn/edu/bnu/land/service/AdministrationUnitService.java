package cn.edu.bnu.land.service;

import java.util.Date;
import java.util.List;

import cn.edu.bnu.land.model.AdministrationUnit;

public interface AdministrationUnitService {
	public void addAdministrationUnit(AdministrationUnit AdministrationUnit);
	public void deleteAdministrationUnit(Integer id);
	public void updateAdministrationUnit(AdministrationUnit AdministrationUnit);
	public AdministrationUnit getByID(Integer id);
	public List<AdministrationUnit> showAdministrationUnit();
}
