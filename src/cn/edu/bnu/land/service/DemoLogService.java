package cn.edu.bnu.land.service;

import java.util.Date;
import java.util.List;

import cn.edu.bnu.land.model.DemolitionLog;

public interface DemoLogService {
	public void addLog(DemolitionLog log);
	public void updatePhoto(DemolitionLog log);
	public List<DemolitionLog> showAll();
	public boolean findByDate(Date date, String sfz);

}
