package cn.edu.bnu.land.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public interface NoticementDAO {
	public Serializable save(Noticement notice);
	public void update(Noticement notice);
	public void delete(Integer id);
	public Noticement get(Integer id);
	public List<Noticement> findAll();
	public List<Noticement> findByDate(Date date);
}
