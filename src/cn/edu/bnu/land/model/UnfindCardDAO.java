package cn.edu.bnu.land.model;

import java.util.List;

public interface UnfindCardDAO {
	public void save(UnfindCard c);
	public void delete(Integer id);
	public void update(UnfindCard c);
	public UnfindCard get(Integer id);
	public List<UnfindCard> findAll();
	public List<UnfindCard> findByProject(String id);
}
