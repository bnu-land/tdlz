package cn.edu.bnu.land.service;

import java.util.List;

import cn.edu.bnu.land.model.Rask;

public interface RaskService {
	public void addRask(Rask r);
	public void modifyRask(Rask r);
	public void removeRask(String id);
	public List<Rask> showAll();
	public Rask getById(String id);
}
