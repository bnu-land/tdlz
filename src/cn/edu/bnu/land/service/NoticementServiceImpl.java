package cn.edu.bnu.land.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.Noticement;
import cn.edu.bnu.land.model.NoticementDAO;
import cn.edu.bnu.land.service.NoticementService;
@Service
public class NoticementServiceImpl implements
NoticementService{

	NoticementDAO noticeDAO;
	
	public NoticementDAO getNoticeDAO() {
		return noticeDAO;
	}

	@Autowired
	public void setNoticeDAO(NoticementDAO noticeDAO) {
		this.noticeDAO = noticeDAO;
	}


	public Noticement getByID(Integer id)
	{
		return noticeDAO.get(id);
	}
	
	public Serializable addNoticement(Noticement notice)
	{
		return noticeDAO.save(notice);
	}
	
	public void deleteNoticement(Integer id)
	{
		noticeDAO.delete(id);
	}
	
	public List<Noticement> showNoticement()
	{
		return noticeDAO.findAll();
	}
	
	public List<Noticement> filterNoticement(Date date)
	{
		return noticeDAO.findByDate(date);
	}
	
	public void modifyNoticement(Noticement n){
		noticeDAO.update(n);
	}
}
