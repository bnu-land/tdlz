package cn.edu.bnu.land.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.URightInfo;

@Service
public class MapService {
	private SessionFactory sessionFactory;
	@SuppressWarnings("unused")
	private UsersManagerService usersManagerService;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Autowired
	public void getUsersManagerService(UsersManagerService usersManagerService) {
		this.usersManagerService = usersManagerService;
	}

	// 获取所有地图资源
	@SuppressWarnings("unchecked")
	public Map<String, Object> getMapResources(String searchKey) {
		// String sql = "FROM URightInfo as ri";
		// if (!searchKey.equals("")) {
		// String hql2 = " WHERE ri.rightId LIKE CONCAT(("
		// + " SELECT LEFT(r.rightId,2) "
		// + " FROM URightInfo AS r "
		// + " WHERE r.url = '"+searchKey +"'),"
		// + " '%')";

		// sql = sql + hql2;
		// }
		String sql = "SELECT  ri.right_id ,ri.right_name,ri.url,ri.enabled,ri.description"
				+ " FROM u_right_info AS ri"
				+ " WHERE ri.right_id LIKE CONCAT(("
				+ " SELECT LEFT(r.right_id,2) "
				+ " FROM u_right_info AS r"
				+ " WHERE r.right_index = '" + searchKey + "'),'%')";
		System.out.println("map sql:" + sql);
		// org.hibernate.Query query =
		// sessionFactory.getCurrentSession().createQuery(sql);
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		// query.setMaxResults(100);
		List<Object[]> rightList = query.list();
		if (rightList.size() == 0) {
			if (searchKey != "") {
				return usersManagerService.getRightInfoList("", "300");
			}
		}

		List<Map<String, Object>> rightMapList = new ArrayList<Map<String, Object>>();
		for (Object[] object : rightList) {
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("rightId", (String) object[0]);
			map.put("url", (String) object[2]);
			// map.put("enabled", (String) object[3]);
			map.put("description", (String) object[4]);

			String rightName = (String) object[0];
			int zeroIndex = rightName.indexOf("00"); // 得到00的位置
			String blankInsert = "";
			switch (zeroIndex) {
			case 2:
				blankInsert = "";
				break;
			case 4:
				blankInsert = "&nbsp;&nbsp;&nbsp;";
				break;
			case 6:
				blankInsert = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				break;
			case 8:
				blankInsert = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				break;
			case -1:
				blankInsert = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				break;
			}
			map.put("rightName", blankInsert + (String) object[1]);

			rightMapList.add(map);
		}

		Map<String, Object> myMapResult = new TreeMap<String, Object>();
		myMapResult.put("root", rightMapList);
		myMapResult.put("success", true);
		return myMapResult;
	}

	// 由地图索引名称获取图层urlList
	@SuppressWarnings("unchecked")
	public Map<String, Object> getMapURLByName() {
		String sql = "SELECT r.rightIndex ,r.url " + "FROM URightInfo AS r";
		System.out.println("map sql:" + sql);
		// SQLQuery query =
		// sessionFactory.getCurrentSession().createSQLQuery(sql);
		org.hibernate.Query query = sessionFactory.getCurrentSession()
				.createQuery(sql);

		List<Object[]> rightList = query.list();
		if (rightList.size() == 0) {
			return null;
		}
		Map<String, Object> rightIndexMap = new HashMap<String, Object>();
		for (Object[] object : rightList) {
			String index = (String) object[0];
			if (index == null || index == "") {
				continue;
			}
			rightIndexMap.put(index, object[1]);
		}
		// 返回值
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("VALUE", rightIndexMap);
		return (map);
	}

}
