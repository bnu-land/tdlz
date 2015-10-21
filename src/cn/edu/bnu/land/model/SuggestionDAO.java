package cn.edu.bnu.land.model;

import java.util.Date;
import java.util.List;

public interface SuggestionDAO {
	public void save(Suggestion suggest);
	public void update(Suggestion suggest);
	public void delete(Integer id);
	public Noticement getNotice(Integer id);
	public List<Suggestion> findAll();
	public List<Suggestion> findByDate(Date date);
	public List<Suggestion> findByCategory(String category);
	public List<Suggestion> findByProjectID(String projectID);
}
