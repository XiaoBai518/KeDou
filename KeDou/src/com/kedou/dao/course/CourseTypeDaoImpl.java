package com.kedou.dao.course;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.CourseType;

@Repository
public class CourseTypeDaoImpl extends BaseDao<CourseType>{

	/**
	 * 根据课程类型IdList 查询课程类型List
	 * @param idList
	 * @return
	 */
	public List<CourseType> findListByIdList(List<Integer> idList) {
		String hql = "from CourseType where id in (:idList)";
		Query query = super.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameterList("idList", idList);
		return query.list();
	}
}
