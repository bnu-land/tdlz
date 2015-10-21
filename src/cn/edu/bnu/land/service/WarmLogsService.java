package cn.edu.bnu.land.service;

import java.util.List;

import cn.edu.bnu.land.model.WarmLogs;

public interface WarmLogsService {
	public void addWarmLogs(WarmLogs wls);
	public void modifyWarmLogs(WarmLogs wls);
	public WarmLogs getByID(Integer id);
	public void removeWarmLogs(Integer id);
	public List<WarmLogs> showAll();
}
