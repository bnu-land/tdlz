package cn.edu.bnu.land.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bnu.land.model.InfoArticle;
import cn.edu.bnu.land.model.ProjectAll;
import cn.edu.bnu.land.model.ProjectAllHome;
import cn.edu.bnu.land.model.DkMonitordata;
import cn.edu.bnu.land.model.DkMonitordataHome;
import cn.edu.bnu.land.model.ProjectYqProgress;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
@Service
public class MonitordataService {
	private DkMonitordataHome DkMonitorDataHome;
	private SessionFactory sessionFactory;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	@Autowired
	public void setDkMonitordataHome(DkMonitordataHome MonitorDataHome){
		this.DkMonitorDataHome=MonitorDataHome;
	}

	//1、添加监测数据
	public void addMonitordata(DkMonitordata dkMonitordata) {
		Session session = sessionFactory.getCurrentSession();	
		session.saveOrUpdate(dkMonitordata);
		
	}


	public void updateMonitordata(DkMonitordata dkMonitordata ) {
		System.out.println("update_monitordata: "+dkMonitordata.getHead());
		Session session = sessionFactory.getCurrentSession(); 
		session.saveOrUpdate(dkMonitordata);	
		
	}

	
	
	
	@Transactional
	public void deleteMonitordatabyIds(String[] recordIds) 
	{

		DkMonitordata result = null;
		Session session = sessionFactory.getCurrentSession();

		try {

			for (int i = 0; i < recordIds.length; i++) {

				result = (DkMonitordata) session.get(DkMonitordata.class,
						Integer.parseInt(recordIds[i]));
				session.delete(result);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}



