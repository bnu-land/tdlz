package cn.edu.bnu.land.service;

import java.util.List;

import cn.edu.bnu.land.model.Projection;

public interface ProjectionService {
	public void addProjection(Projection p);
	public void modifyProjection(Projection p);
	public List<Projection> showAll();
	public List<Projection> showByNumber(String number);
}
