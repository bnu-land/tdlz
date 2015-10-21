package cn.edu.bnu.land.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.edu.bnu.land.model.Noticement;

public interface NoticementService {
	public Serializable addNoticement(Noticement notice);
	public void modifyNoticement(Noticement notice);
	public void deleteNoticement(Integer id);
	public Noticement getByID(Integer id);
	public List<Noticement> showNoticement();
	public List<Noticement> filterNoticement(Date date);
}
