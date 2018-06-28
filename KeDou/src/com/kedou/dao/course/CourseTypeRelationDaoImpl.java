package com.kedou.dao.course;



import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.CourseTypeRelation;
@Repository
public class CourseTypeRelationDaoImpl extends BaseDao<CourseTypeRelation> {

	/**
	 * 根据课程ID 查询相应的 课程类型IdList
	 * @param courseid
	 * @return
	 */
	public List<Integer> findTypeIdBycourseId(int courseid) {
		String hql="select coursetypeId from CourseTypeRelation as ctr where ctr.courseId=:courseid";
		Query query = super.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter("courseid", courseid);
		return query.list();
	}
}
