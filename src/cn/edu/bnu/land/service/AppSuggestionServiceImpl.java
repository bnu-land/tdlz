package cn.edu.bnu.land.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.AppSuggestion;
import cn.edu.bnu.land.model.AppSuggestionDAO;
import cn.edu.bnu.land.service.AppSuggestionService;
@Service
public class AppSuggestionServiceImpl implements AppSuggestionService{

	private AppSuggestionDAO appSuggestDAO;

	public AppSuggestionDAO getAppSuggestDAO() {
		return appSuggestDAO;
	}
	@Autowired
	public void setAppSuggestDAO(AppSuggestionDAO appSuggestDAO) {
		this.appSuggestDAO = appSuggestDAO;
	}
	
	public void addAppSuggestion(AppSuggestion appSuggest)
	{
		appSuggestDAO.save(appSuggest);
	}
	
	public List<AppSuggestion> showAppSuggestion(Integer id)
	{
		return appSuggestDAO.findByID(id);
	}
}
