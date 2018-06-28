package com.kedou.service.course;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.dao.course.BusinessCourseTypeRelationDaoImpl;
import com.kedou.dao.course.CourseDaoImpl;
import com.kedou.dao.course.CourseTypeDaoImpl;
import com.kedou.dao.course.CourseTypeRelationDaoImpl;
import com.kedou.dao.user.collection.CollectionDaoImpl;
import com.kedou.dao.course.CourseBusCtypeRelationDaoImpl;
import com.kedou.entity.BusinessCourseTypeRelation;
import com.kedou.entity.Collection;
import com.kedou.entity.Course;
import com.kedou.entity.CourseType;
import com.kedou.entity.CourseTypeRelation;
import com.kedou.entity.User;
import com.kedou.superentity.SuperCourse;
import com.kedou.entity.CourseBusCtypeRelation;

@Service
@Transactional(readOnly=false,rollbackFor=RuntimeException.class)
public class CourseServiceImpl {

	@Resource
	private CourseDaoImpl courseDaoImpl; 											//课程dao
	@Resource
	private CourseTypeDaoImpl courseTypeDaoImpl;									//课程类型Dao
	@Resource
	private CourseTypeRelationDaoImpl courseTypeRelationDaoImpl;					//课程 课程类型映射Dao
	@Resource
	private BusinessCourseTypeRelationDaoImpl businessCourseTypeRelationDaoImpl;	//商家 课程类型映射 Dao
	@Resource
	private CourseBusCtypeRelationDaoImpl courseBusCtypeRelationDaoImpl;			//课程 商家课程类型映射 Dao
	@Resource
	private CollectionDaoImpl collectionDaoImpl;									//收藏 Dao

	/**
	 * 添加课程
	 * @param c
	 * @throws Exception
	 */
	public void SaveCourse (Course c) throws Exception {
		this.courseDaoImpl.save(c);
	}
	/**
	 * 根据课程Id查询所属商家的ID
	 * @return
	 * @throws Exception 
	 */
	public int findBusidByCourseId(int courseId) throws Exception {
		return this.courseDaoImpl.get(courseId).getBusId();
	}
	/**
	 * 根据课程Id查询课程
	 * @throws Exception 
	 */
	public Course findByCourseId(int courseId) throws Exception {
		return this.courseDaoImpl.get(courseId);
	}
	/**
	 * 根据课程Id查询课程类型List
	 */
	public List<CourseType> findCourseTypeListByCourseId(int courseId) {
		List<Integer> TypeIdList = this.courseTypeRelationDaoImpl.findTypeIdBycourseId(courseId);
		return this.courseTypeDaoImpl.findListByIdList(TypeIdList);
		
	}
	/**
	 * 创建 收藏课程List(SuperCourse)
	 * @param courseList
	 * @return
	 */
	public List<SuperCourse> createSuperCourseByCollection(List<Course> courseList) {
			if(courseList!=null&&courseList.size()!=0) {
				List<Integer> idList = new ArrayList<Integer>();
				for(Course c:courseList) {
					idList.add(c.getCourseId());
				}
				//查看该用户收藏了哪些课程
				int userId = ((User)SecurityUtils.getSubject().getSession().getAttribute("loginUser")).getUserId();
				
				Map<Integer,Collection> collectionMap = this.collectionDaoImpl.findMapByCourseList(idList,userId);
			
			List<SuperCourse> superCourseList = new ArrayList<SuperCourse>();
			if(collectionMap!=null&&collectionMap.size()!=0) {
				for(Course c:courseList) {
					if(collectionMap.get(c.getCourseId())!=null){
						 superCourseList.add(new SuperCourse(c, 1));
					}else {
						superCourseList.add(new SuperCourse(c, 0));
					}
				}
			}else {
				courseList.forEach((c) -> superCourseList.add(new SuperCourse(c,0)));
			}
			return superCourseList;
		  }
			
		return null;
	}
	/**
	 * 保存课程和课程类型关系映射 的关系
	 * @param ctr
	 * @throws Exception
	 */
	public void SaveCourseTypeRelation (CourseTypeRelation ctr) throws Exception {
		if(ctr!=null) {
			this.courseTypeRelationDaoImpl.save(ctr);
		}
	}
	/**
	 * 保存 课程 和 商家课程关系映射 的关系
	 * @param cbctr
	 * @throws Exception
	 */
	public void SaveCourseBusCtypeRelation(CourseBusCtypeRelation cbctr) throws Exception {
		if(cbctr!=null) {
			this.courseBusCtypeRelationDaoImpl.save(cbctr);
		}
		
	}
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
			if(bct!=null&&bct.size()>0) {
				for(BusinessCourseTypeRelation b : bct){
					if(!this.courseBusCtypeRelationDaoImpl.courseListByType(b.getId()).isEmpty()){
						
						//通过机构类型id查询课程id
						List<CourseBusCtypeRelation> ctrs = this.findByBusCourseTypeId(b.getId());
						
						//查询类型下的课程列表
						List<Course> cl = new ArrayList<Course>();
						for(CourseBusCtypeRelation ctr : ctrs){
							 cl.add(this.courseDaoImpl.get(ctr.getCourseId()));
						}
						courseList.put(this.courseTypeDaoImpl.get(b.getCourseTypeId()), cl);
					}
					
				}
				return courseList;
			}else {
				return null;
			}
		
	}
	/**
	 * @desc 按类型查询课程
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
			if(!this.courseBusCtypeRelationDaoImpl.courseListByType(b.getId()).isEmpty()){
				
				//通过机构类型id查询课程id
				List<CourseBusCtypeRelation> ctrs = this.findByBusCourseTypeId(b.getId());
				
				List<Course> cl = new ArrayList<Course>();
				for(CourseBusCtypeRelation ctr : ctrs){
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
	 * 通过课程类型Id 和 机构Id 查询 机构和课程类型的映射Id
	 * @param coursetypeid
	 * @param busid
	 * @return
	 * @throws Exception
	 */
	public int findBusCTypeIdByCourseTypeIdAndBusId(int coursetypeid,int busid) throws Exception {
		return this.businessCourseTypeRelationDaoImpl.findBusCTypeIdByCourseTypeIdAndBusId(coursetypeid, busid);
	}
	/**
	 * 
	 * @desc 通过机构课程类型id查询课程id
	 * @author 原源
	 * @createDate 2018年5月10日
	 * @return
	 * @throws Exception
	 */
	public List<CourseBusCtypeRelation> findByBusCourseTypeId(int  buscourtypeId) throws Exception{
		Object [] params = {buscourtypeId};
		
		return this.courseBusCtypeRelationDaoImpl.findByBusCourseTypeId(params);
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
		String hql = " from BusinessCourseTypeRelation  where busId = ? and courseTypeId = ?";
		int id = this.businessCourseTypeRelationDaoImpl.findOne(hql, param).getId();
		return id;
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
	 * @desc 通过搜索内容查询课程(广告位)
	 * @author 原源
	 * @createDate 2018年5月10日
	 * @return
	 * @throws Exception
	 */
	public List<Course> findReCommendCourseByCourseList(List<Course>courseList){
		List<Integer> idList = new ArrayList<Integer>();
		 for(Course c:courseList) {
			 idList.add(c.getCourseId()) ;
		 }
		
		 List<Integer> recommemdIdList =  this.courseDaoImpl.findReCommendCourseId(idList);
		
		 recommemdIdList.forEach((c)->  System.out.println("推荐的课程Id"+c));
		 return this.courseDaoImpl.findCourseList(recommemdIdList);

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
	
	/**
	 * 根据课程ID列表查询课程列表
	 * @param courseid
	 * @return
	 */
	public List<Course> findCourseList(List<Integer>params) {
		if(!params.isEmpty()) {
			return this.courseDaoImpl.findCourseList(params);
		}else {
			return null;
		}

	}
	public Map<Integer,Course> findCourseMapByIdList(List<Integer> courseidList) {
		if(!courseidList.isEmpty()) {
				List<Course> courselist = this.courseDaoImpl.findCourseList(courseidList);
				 Map<Integer,Course> courseMap = new HashMap<Integer, Course>();
				 for(Course c:courselist) {
					 courseMap.put(c.getCourseId(),c);
				 }
				 return courseMap;
		}else {
			return null;
		}
		
	}
	/**
	 * 锁定课程
	 * @param id
	 */
	public void lockCou(int[] array) {
		for(int i=0;i<array.length;i++) {
			try {
				courseDaoImpl.lockCou(array[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	/**
	 * 根据商家Id 分页查询课程列表
	 * @throws Exception 
	 */
	public List<Course> findAllCourseByBusId(int busid,int pageNum) throws Exception {
		
		return this.courseDaoImpl.findAllCourseByBusId(busid, pageNum);
	}
	/**
	 * 查询全部课程
	 * @return
	 */
	public List<Course> searchAllCou(){
		try {
			return courseDaoImpl.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	/**
	 * 根据机构Id 查询课程总数
	 * @param busid
	 * @return
	 */
	public long findCourseCount(int busid) {
		return this.courseDaoImpl.findCountByBusId(busid);
	}
	/**
	 * 根据课程列表查询相应的类型列表
	 * @param clist
	 * @return
	 * @throws Exception
	 */
	public List<SuperCourse> findTypeByCourseList(List<Course> clist) throws Exception {
		
		List<SuperCourse> typelist = new ArrayList<SuperCourse>();
		// 每个课程 的类型列表
		List<Integer> idtemp = null;
		//每个课程的 类型名字列表
		List<String> nametemp = null;
		for(Course c:clist) {
			// 每个课程 的类型列表
			idtemp = this.courseTypeRelationDaoImpl.findTypeIdBycourseId(c.getCourseId());
			System.out.println("类型列表"+idtemp.size());
			//每个课程的 类型名字列表
			nametemp= new ArrayList<String>();
			for(Integer i :idtemp) {
				nametemp.add(this.courseTypeDaoImpl.get(i).getTypeName());
			}
			typelist.add(new SuperCourse(c, nametemp));
		}
		return typelist;
	}
	public List<String> autoComplete(String query)  {
		List<String> a = this.courseDaoImpl.autoComplete(query);
		System.out.println(a.size());
		return a;
	}
	
}
