package com.kedou.dao.course;

import org.springframework.stereotype.Repository;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.BusinessCourseTypeRelation;

@Repository
public class BusinessCourseTypeRelationDaoImpl extends BaseDao<BusinessCourseTypeRelation> {
	
	public int findBusCTypeIdByCourseTypeIdAndBusId(int coursetypeid,int busid) throws Exception {
		String hql = "from BusinessCourseTypeRelation as b where b.courseTypeId=? and b.busId=?";
		
		Integer [] params = {coursetypeid,busid};
		return super.findOne(hql, params).getId();
	}
}
