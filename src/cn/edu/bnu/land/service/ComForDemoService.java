package cn.edu.bnu.land.service;

import java.util.List;

import cn.edu.bnu.land.model.ComForDemolition;
import cn.edu.bnu.land.model.FarmersInformation;

public interface ComForDemoService {
	public void addComForDemolition(ComForDemolition cfd);
	public void changeComForDemolition(ComForDemolition cfd);
	public List<ComForDemolition> showAll();
	public List<ComForDemolition> showByFarmer(Integer id);
	public ComForDemolition getByID(Integer id);
	public List<ComForDemolition> showByProject(String id);

}
