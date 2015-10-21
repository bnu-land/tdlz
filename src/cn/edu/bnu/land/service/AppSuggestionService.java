package cn.edu.bnu.land.service;

import java.util.List;

import cn.edu.bnu.land.model.AppSuggestion;
import cn.edu.bnu.land.model.AppSuggestionDAO;

public interface AppSuggestionService {
	
	public List<AppSuggestion> showAppSuggestion(Integer id);
	public void addAppSuggestion(AppSuggestion appSuggest);
	
}
