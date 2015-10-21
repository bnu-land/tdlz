package cn.edu.bnu.land.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.AbnmDktranssupervision;
import cn.edu.bnu.land.model.AbnmDktranssupervisionHome;
import cn.edu.bnu.land.model.AbnmWholesupervision;
import cn.edu.bnu.land.model.AbnmWholesupervisionHome;
import cn.edu.bnu.land.model.InfoVote;
import cn.edu.bnu.land.model.RwTable;

@Service
public class AbnmWholeSupervisionService {
	private AbnmWholesupervisionHome abnmWholesupervisionHome;
	private AbnmDktranssupervisionHome abnmDktranssupervisionHome;
	private RwTable rwTable;
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Autowired
	public void setAbnmWholesupervisionHome(
			AbnmWholesupervisionHome abnmWholesupervisionHome) {
		this.abnmWholesupervisionHome = abnmWholesupervisionHome;

	}

	/*
	 * 
	 * @Description:得到异常监管任务列表，并且可以进行列表搜索也
	 * 
	 * @Params: String start, String limit, String searchKeyword, String
	 * searchDate
	 * 
	 * @Return:Map<String, Object> myMapResult
	 * 
	 * @Author:LiuFang
	 * 
	 * @Time:20140408
	 */
	public Map<String, Object> getAbwsList(String start, String limit,
			String searchKeyword, String searchDate) {

		String hql = "";
		String hql1 = "from AbnmWholesupervision as abnmWholesupervision ";

		// 搜索日期不为空，搜索关键词空
		if (!searchDate.equals("") && searchKeyword.equals("")) {
			hql = hql1 + " where abnmWholesupervision.abwsDate>='" + searchDate
					+ "'";
		}
		// 搜索日期不为空，搜索关键词不为空
		else if (!searchDate.equals("") && !searchKeyword.equals("")) {
			String hql2 = " where ( abnmWholesupervision.abwsTitle like '%"
					+ searchKeyword + "%'"
					+ " or abnmWholesupervision.abwsClass like '%"
					+ searchKeyword + "%'"
					+ " and abnmWholesupervision.abwsDate>='" + searchDate
					+ "')";
			hql = hql1 + hql2;

		}
		// 搜索日期空，搜索关键词不为空
		else if (searchDate.equals("") && !searchKeyword.equals("")) {
			String hql3 = " where ( abnmWholesupervision.abwsTitle like '%"
					+ searchKeyword + "%'"
					+ " or abnmWholesupervision.abwsClass like '%"
					+ searchKeyword + "%')";
			hql = hql1 + hql3;
		}
		// 搜索日期为空，搜索关键词为空
		else if (searchDate.equals("")
				&& (searchKeyword.equals("") || searchKeyword.isEmpty())) {
			hql = hql1;
		}
		;

		String totalConut = null;
		List<AbnmWholesupervision> results = null;
		try {
			// System.out.println(hql);
			org.hibernate.Query query = sessionFactory.getCurrentSession()
					.createQuery(hql);
			totalConut = String.valueOf(query.list().size());
			// System.out.println(totalConut);
			query.setFirstResult(Integer.parseInt(start));
			query.setMaxResults(Integer.parseInt(limit));
			results = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> myMapResult = new TreeMap<String, Object>();
		myMapResult.put("total", totalConut);
		myMapResult.put("root", results);
		// System.out.println("myMapResult "+gridId+" "+myMapResult);

		return myMapResult;

	}

	/*
	 * 
	 * @Description:更新全过程异常监管意见
	 * 
	 * @Params:AbnmWholesupervision abws
	 * 
	 * @Return:void
	 * 
	 * @Author:LiuFang
	 * 
	 * @Time:20140225
	 */
	public void updateAbws(AbnmWholesupervision abws) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(abws);
	}

	/*
	 * 
	 * @Description:从交易预警面板提交异常信息至全过程异常，更新地块交易预警信息中与提交异常信息相关的字段
	 * 
	 * @Params:AbnmWholesupervision abws
	 * 
	 * @Return:void
	 * 
	 * @Author:LiuFang
	 * 
	 * @Time:20140305
	 */
	public void addAbws(AbnmWholesupervision abws) {
		Session session = sessionFactory.getCurrentSession();
		session.save(abws);
		updateAbdkAboutWhole(abws.getSourceRecordid());
	}

	/*
	 * 
	 * @Description:通过id获得地块交易预警信息，更新其中与提交异常信息相关的字段
	 * 
	 * @Params:String sourceRecordid
	 * 
	 * @Return:void
	 * 
	 * @Author:LiuFang
	 * 
	 * @Time:20140305
	 */
	private void updateAbdkAboutWhole(String sourceRecordid) {
		Calendar ca = Calendar.getInstance();
		Date now = ca.getTime();

		AbnmDktranssupervision result = null;
		Session session = sessionFactory.getCurrentSession();
		try {
			// System.out.println(hql);
			result = (AbnmDktranssupervision) session.get(
					AbnmDktranssupervision.class,
					Integer.parseInt(sourceRecordid));
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.setAbdkTowholetime(now);
		result.setAbdkIstowhole("是");

		session.update(result);
	}

	/*
	 * 
	 * @Description:删除信息，点击“批量删除”按钮或者点击每条信息后的删除图标逐条删除
	 * 
	 * @Params:String[] ids
	 * 
	 * @Return:void
	 * 
	 * @Author:LiuFang
	 * 
	 * @Time:20140408
	 */
	public void delete_abwsByIds(String[] ids) {
		AbnmWholesupervision result = null;
		Session session = sessionFactory.getCurrentSession();

		try {
			for (int i = 0; i < ids.length; i++) {
				result = (AbnmWholesupervision) session.get(
						AbnmWholesupervision.class, Integer.parseInt(ids[i]));
				session.delete(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * 
	 * @Description:得到异常监管任务的列表
	 * 
	 * @Params:String start, String limit
	 * 
	 * @Return:Map<String, Object> myMapResult
	 * 
	 * @Author:LiuFang
	 * 
	 * @Time:20140408
	 */
	public Map<String, Object> getAbwsRwList(String start, String limit) {
		String hql = "from RwTable as rwTable where rwClass = '异常监管'";
		String totalConut = null;
		List<RwTable> results = null;
		try {
			// System.out.println(hql);
			org.hibernate.Query query = sessionFactory.getCurrentSession()
					.createQuery(hql);
			totalConut = String.valueOf(query.list().size());
			// System.out.println(totalConut);
			query.setFirstResult(Integer.parseInt(start));
			query.setMaxResults(Integer.parseInt(limit));
			results = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> myMapResult = new TreeMap<String, Object>();
		myMapResult.put("total", totalConut);
		myMapResult.put("root", results);
		// System.out.println("myMapResult "+gridId+" "+myMapResult);

		return myMapResult;
	}

	/*
	 * 
	 * @Description:包括批量删除按钮和逐条删除的删除图标
	 * 
	 * @Params:String[] ids
	 * 
	 * @Return:void
	 * 
	 * @Author:LiuFang
	 * 
	 * @Time:20140408
	 */
	public void deleteAbwsRWByIds(String[] rwIds) {

		List<RwTable> results = null;
		Session session = sessionFactory.getCurrentSession();
		System.out.println(rwIds);
		try {

			for (int i = 0; i < rwIds.length; i++) {

				String hql = "delete RwTable where rwId = '" + rwIds[i] + "'";
				session.createQuery(hql).executeUpdate();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * 
	 * @Description:增加新的异常监管任务
	 * 
	 * @Params:RwTable rw, String abwsId
	 * 
	 * @Return:void
	 * 
	 * @Author:LiuFang
	 * 
	 * @Time:20140408
	 */
	public void addAbwsRW(RwTable rw) {
		Session session = sessionFactory.getCurrentSession();
		Calendar ca = Calendar.getInstance();
		Date now = ca.getTime();
		rw.setRwGeneratetime(now);
		// System.out.println("add_abwsRW: RwGeneratetime is  " +
		// rw.getRwGeneratetime());
		session.saveOrUpdate(rw);
	}

	/*
	 * 
	 * @Description:更新全过程异常监管任务表的信息
	 * 
	 * @Params: RwTable rw
	 * 
	 * @Return:void
	 * 
	 * @Author:LiuFang
	 * 
	 * @Time:20140408
	 */
	public void updateAbwsRW(RwTable rw) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(rw);
	}

	/*
	 * 
	 * @Description:同时更新全过程异常监管信息的rwId和是否下达任务字段
	 * 
	 * @Params:String abwsId, String rwId
	 * 
	 * @Return:void
	 * 
	 * @Author:LiuFang
	 * 
	 * @Time:20140408
	 */
	public void updateAbwsRwid(String abwsId, String rwId) {
		AbnmWholesupervision result = null;
		Session session = sessionFactory.getCurrentSession();
		result = (AbnmWholesupervision) session.get(AbnmWholesupervision.class,
				Integer.parseInt(abwsId));
		result.setRwId(rwId);
		result.setAbwsIsTaskAssigned("是");
		session.update(result);
	}

	public Map<String, Object> getXunchaRWList(String start, String limit) {
		String hql = "from RwTable as rwTable";
		String totalConut = null;
		List<RwTable> results = null;
		try {
			// System.out.println(hql);
			org.hibernate.Query query = sessionFactory.getCurrentSession()
					.createQuery(hql);
			totalConut = String.valueOf(query.list().size());
			// System.out.println(totalConut);
			query.setFirstResult(Integer.parseInt(start));
			query.setMaxResults(Integer.parseInt(limit));
			results = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> myMapResult = new TreeMap<String, Object>();
		myMapResult.put("total", totalConut);
		myMapResult.put("root", results);
		// System.out.println("myMapResult "+gridId+" "+myMapResult);

		return myMapResult;
	}


}