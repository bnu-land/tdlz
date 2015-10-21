package cn.edu.bnu.land.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.DbBackuprecord;
import cn.edu.bnu.land.model.DbBackuprecordHome;

@Service
public class DBBackupRecoeryService {
	//private DbBackuprecordHome backuprecordHome;
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

//	@Autowired
//	public void setDbBackuprecordHome(DbBackuprecordHome backuprecordHome) {
//		this.backuprecordHome = backuprecordHome;
//	}

	public Map<String, Object> getBackupData(String start, String limit) {
		// return this.infoArticleHome.selectInfoArticleList(start, limit);
		// System.out.println("time is getInfoArticleList @InfoArticleService ");
		// System.out.println("start"+start);
		// System.out.println("limit"+limit);
		String hql = " from DbBackuprecord ";
		System.out.println(hql);
		String totalConut = null;
		List<DbBackuprecord> results = null;
		try {
			org.hibernate.Query query = sessionFactory.getCurrentSession().createQuery(hql);
			totalConut = String.valueOf(query.list().size());
			//System.out.println(totalConut);
			query.setFirstResult(Integer.parseInt(start));
			query.setMaxResults(Integer.parseInt(limit));
			results = (List<DbBackuprecord>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> myMapResult = new TreeMap<String, Object>();
		myMapResult.put("total", totalConut);
		myMapResult.put("root", results);
		System.out.println("backup service: count: "+totalConut);

		return myMapResult;

	}


}