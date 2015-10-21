package cn.edu.bnu.land.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.AbnmDktranssupervision;
import cn.edu.bnu.land.model.AbnmIndex;
import cn.edu.bnu.land.model.AbnmIndexHome;

@Service
public class AbnmIndexService {
	private AbnmIndexHome AbnmIndexHome;
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Autowired
	public void setAbnmIndexHome(AbnmIndexHome abnmIndexHome) {
		this.AbnmIndexHome = abnmIndexHome;

	}

	/*
	 * 
	 * @Description:通过dkId获得相应的交易预警指标数值，
	 * 
	 * @Params:String start, String limit, String dkId
	 * 
	 * @Return: Map<String, Object> myMapResult
	 * 
	 * @Author: LiuFang
	 * 
	 * @Time: 20140225
	 */
	public Map<String, Object> getAbiList(String start, String limit,
			String dkId) {

		String hql = "from AbnmIndex where dkId=" + dkId;

		String totalConut = null;
		List<AbnmIndex> results = null;
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

		return myMapResult;

	}

	/*
	 * 
	 * @Description:增加新的指标系数
	 * 
	 * @Params:AbnmIndex abnmIndex
	 * 
	 * @Return: void
	 * 
	 * @Author: LiuFang
	 * 
	 * @Time: 20140306
	 */
	public void addAbi(AbnmIndex abnmIndex) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(abnmIndex);
	}

	/*
	 * 
	 * @Description:通过指标的id，删除指标系数
	 * 
	 * @Params:AbnmIndex abnmIndex
	 * 
	 * @Return: void
	 * 
	 * @Author: LiuFang
	 * 
	 * @Time: 20140304
	 */
	public void deleteAbiByIds(String[] ids) {
		AbnmIndex result = null;
		Session session = sessionFactory.getCurrentSession();

		try {
			for (int i = 0; i < ids.length; i++) {
				result = (AbnmIndex) session.get(AbnmIndex.class,
						Integer.parseInt(ids[i]));
				session.delete(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 
	 * @Description:更新指标系数
	 * 
	 * @Params:AbnmIndex abnmIndex
	 * 
	 * @Return: void
	 * 
	 * @Author: LiuFang
	 * 
	 * @Time: 20140306
	 */
	public void updateAbi(AbnmIndex abnmIndex) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(abnmIndex);

	}

}