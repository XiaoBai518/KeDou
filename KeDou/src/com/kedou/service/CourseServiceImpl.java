package com.kedou.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.dao.BusinessCourseTypeRelationDaoImpl;
import com.kedou.dao.CourseDaoImpl;
import com.kedou.dao.CourseTypeDaoImpl;
import com.kedou.dao.CourseTypeRelationDaoImpl;
import com.kedou.entity.BusinessCourseTypeRelation;
import com.kedou.entity.Course;
import com.kedou.entity.CourseType;
import com.kedou.entity.CourseTypeRelation;

@Service
@Transactional(readOnly=false)
public class CourseServiceImpl {

	@Resource
	private CourseDaoImpl courseDaoImpl; 
	@Resource
	private CourseTypeDaoImpl courseTypeDaoImpl;
	@Resource
	private BusinessCourseTypeRelationDaoImpl businessCourseTypeRelationDaoImpl;
	@Resource
	private CourseTypeRelationDaoImpl courseTypeRelationDaoImpl;
	
	/**
	 * 
	 * @desc 按类型查询课程(默认个数)
	 * @author 原源
	 * @createDate 2018年5月10日
	 * @param busId
	 * @param bct
	 * @param pagesize
	 * @return
	 * @throws Exception
	 */
	public Map<CourseType,List<Course>> courseList(int busId,List<BusinessCourseTypeRelation> bct)  throws Exception{
		
		Map<CourseType,List<Course>> courseList = new HashMap<CourseType, List<Course>>();
		
		//获得该机构的热门商品列表
			this.gethotCourseLists(busId, courseList);
		
		for(BusinessCourseTypeRelation b : bct){
			if(!this.courseDaoImpl.courseListByType(b.getId()).isEmpty()){
				
				//通过机构类型id查询课程id
				List<CourseTypeRelation> ctrs = this.findByBusCourseTypeId(b.getId());
				
				//查询类型下的课程列表
				List<Course> cl = new ArrayList<Course>();
				for(CourseTypeRelation ctr : ctrs){
					 cl.add(this.courseDaoImpl.get(ctr.getCourseId()));
				}
				courseList.put(this.courseTypeDaoImpl.get(b.getCourseTypeId()), cl);
			}
			
		}
		return courseList;
	}
	/**
	 * @desc 按类型查询课程(默认个数)
	 * @param busId
	 * @param bct
	 * @param pagesize
	 * @return
	 * @throws Exception
	 */
	public Map<CourseType,List<Course>> courseList(int busId,List<BusinessCourseTypeRelation> bct,int pagesize)  throws Exception{
		
		Map<CourseType,List<Course>> courseList = new HashMap<CourseType, List<Course>>();
		
		//获得该机构的热门商品列表
		this.gethotCourseLists(busId, courseList);
	
		
		for(BusinessCourseTypeRelation b : bct){
			if(!this.courseDaoImpl.courseListByType(b.getId()).isEmpty()){
				
				//通过机构类型id查询课程id
				List<CourseTypeRelation> ctrs = this.findByBusCourseTypeId(b.getId());
				
				List<Course> cl = new ArrayList<Course>();
				for(CourseTypeRelation ctr : ctrs){
					 cl.add(this.courseDaoImpl.get(ctr.getCourseId()));
				}
				courseList.put(this.courseTypeDaoImpl.get(b.getCourseTypeId()), cl);
			}
			
		}
		return courseList;
	}
	
	/**
	 * 
	 * @desc 指定课程类型查询课程
	 * @author 原源
	 * @createDate 2018年5月10日
	 * @return
	 * @throws Exception
	 */
	public Map<CourseType,List<Course>> courseListsByCourseType(int typeId,int busId)  throws Exception{
		Map<CourseType,List<Course>> courseList = new HashMap<CourseType, List<Course>>();
		
		//通过机构id和课程类型id查询对应的id号
		int busCourseTypeId =findByBusIdCourseTypeId(busId,typeId);
		
		//获得课程类型实体
		CourseType ct = this.courseTypeDaoImpl.get(typeId);
		
		//将 课程类型实体 作为键 该课程类型的下的课程列表作为 值
		courseList.put(ct,this.courseDaoImpl.courseListByTypes(this.courseTypeRelationDaoImpl.get(busCourseTypeId).getCourseId()) );
		return courseList;
	}
	
	/**
	 * @desc 获得一个商家热门课程列表
	 * @author 原源
	 * @param busId
	 * @return
	 * @throws Exception
	 */
	public Map<CourseType,List<Course>> gethotCourseLists(int busId)  throws Exception{
		Map<CourseType,List<Course>> courseList = new HashMap<CourseType, List<Course>>();
		CourseType c = new CourseType();
		c.setTypeName("热门课程");
		c.setId(0);
		courseList.put(c, this.courseDaoImpl.courseListByHots(busId));
		return courseList;
	}
	/**
	 * @desc 获得一个商家热门课程列表
	 * @author 原源
	 * @param busId
	 * @param courseList
	 * @return
	 * @throws Exception
	 */
	public Map<CourseType,List<Course>> gethotCourseLists(int busId,Map<CourseType,List<Course>> courseList)  throws Exception{
		CourseType c = new CourseType();
		c.setTypeName("热门课程");
		c.setId(0);
		courseList.put(c, this.courseDaoImpl.courseListByHots(busId));
		return courseList;
	}
	
	
	/**
	 * 
	 * @desc 通过机构课程类型id查询课程id
	 * @author 原源
	 * @createDate 2018年5月10日
	 * @return
	 * @throws Exception
	 */
	public List<CourseTypeRelation> findByBusCourseTypeId(int  buscourtypeId) throws Exception{
		Object [] params = {buscourtypeId};
		String hql = " from CourseTypeRelation  where buscourtypeId = ?";
		return this.courseTypeRelationDaoImpl.findByProperty(hql,params);
	}
	/**
	 * 根据商家课程类型关系 ID 查找课程类型列表
	 * @param bct
	 * @return
	 * @throws Exception
	 */
	public List<CourseType> findByIdList(List<BusinessCourseTypeRelation> bct) throws Exception{
		List<CourseType> ct = new ArrayList<CourseType>();
		for(BusinessCourseTypeRelation b : bct){
			int typeId = this.businessCourseTypeRelationDaoImpl.get(b.getId()).getCourseTypeId();
			ct.add(this.courseTypeDaoImpl.get(typeId));
		}
		return ct;
	}
	/**
	 * 根据商家ID 查找 商家课程类型关系 实体
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<BusinessCourseTypeRelation> findTypeByBusinessId(int busid) throws Exception{
		Object[] param = {busid};
		String hql = " from BusinessCourseTypeRelation  where busId = ?";
		List<BusinessCourseTypeRelation> bl = this.businessCourseTypeRelationDaoImpl.findByProperty(hql, param);
		if(bl.size()!=0) {
			return bl;		
		}
			return null;	
	}
	
	/**
	 * 
	 * @desc 通过机构id和课程类型id查询对应的id号
	 * @author 原源
	 * @createDate 2018年5月10日
	 * @return
	 * @throws Exception
	 */
	public int findByBusIdCourseTypeId(int busId,int typeId) throws Exception{
		Object[] param = {busId,typeId};
		String hql = " from BusinessCourseTypeRelation  where busId = ? & courseTypeId = ?";
		int id = this.businessCourseTypeRelationDaoImpl.findOne(hql, param).getId();
		return id;
	}
	/**
	 * 
	 * @desc 通过搜索内容查询课程(第2页之后)
	 * @author 原源
	 * @createDate 2018年5月10日
	 * @return
	 * @throws Exception
	 */
	public List<Course> findBySearchPage(int pageNum,int pageSize,String searchSentence){
		return this.courseDaoImpl.findBySearchSentencePage(pageNum, pageSize, searchSentence);
	}
	
	/**
	 * 
	 * @desc 通过搜索内容查询课程(升序)
	 * @author 原源
	 * @createDate 2018年5月10日
	 * @return
	 * @throws Exception
	 */
	public List<Course> findBySearchPageAsc(int pageNum,int pageSize,String searchSentence){
		return this.courseDaoImpl.findBySearchSentencePageAsc(pageNum, pageSize, searchSentence);
	}
	
	/**
	 * 
	 * @desc 通过搜索内容查询课程(降序)
	 * @author 原源
	 * @createDate 2018年5月10日
	 * @return
	 * @throws Exception
	 */
	public List<Course> findBySearchPageDesc(int pageNum,int pageSize,String searchSentence){
		return this.courseDaoImpl.findBySearchSentencePageDesc(pageNum, pageSize, searchSentence);
	}
	
	/**
	 * 
	 * @desc 通过搜索内容查询课程(首页)
	 * @author 原源
	 * @createDate 2018年5月10日
	 * @return
	 * @throws Exception
	 */
	public List<Course> findBySearch(String searchSentence){
		return this.courseDaoImpl.findBySearchSentence(searchSentence);
	}
	
	/**
	 * 
	 * @desc 通过搜索内容查询课程(广告位)
	 * @author 原源
	 * @createDate 2018年5月10日
	 * @return
	 * @throws Exception
	 */
	public List<Course> findBySearchAd(String searchSentence){
		return this.courseDaoImpl.findBySearchSentenceAd(searchSentence);
	}
	
	/**
	 * 
	 * @desc 通过搜索内容查询课程(所有)
	 * @author 原源
	 * @createDate 2018年5月10日
	 * @return
	 * @throws Exception
	 */
	public List<Course> findAllBySearch(String searchSentence){
		return this.courseDaoImpl.findAllBySearchSentence(searchSentence);
	}
}
