package cn.edu.bnu.land.service;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.InfoChannel;
import cn.edu.bnu.land.model.InfoChannelHome;





@Service
public class InfoChannelService {
	private InfoChannelHome infoChannelHome;
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Autowired
	public void setInfoChannelHome(InfoChannelHome infoChannelHome){
		this.infoChannelHome=infoChannelHome;
	}
	
	

	public List<InfoChannel> listInfoChannel() {
		String hql = "from InfoChannel as infoChannel ";
		List<InfoChannel> results = null;
		try {
			org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(hql);
			results = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return results;
	}
	
	

}
