package cn.edu.bnu.land.service;

import java.util.List;

import cn.edu.bnu.land.model.FarmersInformation;

public interface FarmersInformationService {
	public void addFarmersInformation(FarmersInformation FarmersInformation);
	public void deleteFarmersInformation(Integer id);
	public void updateFarmersInformation(FarmersInformation FarmersInformation);
	public FarmersInformation getByID(Integer id);
	public List<FarmersInformation> showFarmersInformation();

}