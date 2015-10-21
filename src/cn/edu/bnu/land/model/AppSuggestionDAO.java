package cn.edu.bnu.land.model;

import java.util.List;

public interface AppSuggestionDAO {
	public void save(AppSuggestion suggest);
	public void update(AppSuggestion suggest);
	public void delete(Integer id);
	public AppSuggestion getAppSuggest(Integer id);
	public List<AppSuggestion> findByID(Integer id);
	public List<AppSuggestion> findAll();
}
