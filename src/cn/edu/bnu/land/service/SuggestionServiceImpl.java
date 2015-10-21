package cn.edu.bnu.land.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.Noticement;
import cn.edu.bnu.land.model.Suggestion;
import cn.edu.bnu.land.model.SuggestionDAO;
import cn.edu.bnu.land.service.SuggestionService;
@Service
public class SuggestionServiceImpl implements SuggestionService{
	
	SuggestionDAO suggestDAO;
	
	public SuggestionDAO getSuggestDAO() {
		return suggestDAO;
	}
	@Autowired
	public void setSuggestDAO(SuggestionDAO suggestDAO) {
		this.suggestDAO = suggestDAO;
	}
	
	public Noticement getNoticeByID(Integer id)
	{
		return suggestDAO.getNotice(id);
	}
	
	public void addSuggestion(Suggestion suggest)
	{
		suggestDAO.save(suggest);
	}
	
	public void deleteSuggestion(Integer id)
	{
		suggestDAO.delete(id);
	}
	
	public List<Suggestion> showSuggestion()
	{
		return suggestDAO.findAll();
	}
	
	public List<Suggestion> filterSuggestion(Date date)
	{
		return suggestDAO.findByDate(date);
	}
	
	public String Statistic(String category)
	{
		List<Suggestion> suggest = suggestDAO.findByCategory(category);
		String result = "";
		int point1 = 0;
		int point2 = 0;
		int point3 = 0;
		int point4 = 0;
		int point5 = 0;
		
		for (Suggestion s : suggest) {
			switch (s.getGrade().intValue()) {
			case 1:
				point1++;
				break;
			case 2:
				point2++;
				break;
			case 3:
				point3++;
				break;
			case 4:
				point4++;
				break;
			case 5:
				point5++;
				break;
				

			default:
				break;
			}
		}
		
		result += "\"point1\":" + String.valueOf(point1) +
				",\"point2\":" + String.valueOf(point2) + 
				",\"point3\":" + String.valueOf(point3) + 
				",\"point4\":" + String.valueOf(point4) + 
				",\"point5\":" + String.valueOf(point5);
		
		return result;
	}
}
