package cn.edu.bnu.land.service;

import java.util.List;

import cn.edu.bnu.land.model.UnfindCard;

public interface UnfindCardService {
	public void addUnfindCard(UnfindCard c);
	public void removeUnfindCard(Integer id);
	public void modifyUnfindCard(UnfindCard c);
	public UnfindCard getByID(Integer id);
	public List<UnfindCard> showAll();
	public List<UnfindCard> showByProject(String id);
}
