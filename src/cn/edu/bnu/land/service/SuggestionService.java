package cn.edu.bnu.land.service;


import java.util.Date;
import java.util.List;

import cn.edu.bnu.land.model.Noticement;
import cn.edu.bnu.land.model.Suggestion;


public interface SuggestionService {
	public void addSuggestion(Suggestion suggest);
	public void deleteSuggestion(Integer id);
	public Noticement getNoticeByID(Integer id);
	public List<Suggestion> showSuggestion();
	public List<Suggestion> filterSuggestion(Date date);
	public String Statistic(String category);
}
