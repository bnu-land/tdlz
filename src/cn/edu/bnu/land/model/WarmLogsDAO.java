package cn.edu.bnu.land.model;

import java.util.List;

public interface WarmLogsDAO {
	public void save(WarmLogs wls);
	public void update(WarmLogs wls);
	public void delete(Integer id);
	public WarmLogs get(Integer id);
	public List<WarmLogs> showAll();
	public List<WarmLogs> findByProjectID(String projectID);
}
