package com.kedou.service.torder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.dao.torder.TorderDaoImpl;
import com.kedou.entity.Course;
import com.kedou.entity.Torder;
import com.kedou.entity.User;
import com.kedou.superentity.SuperChart;
import com.kedou.superentity.SuperCourse;
import com.kedou.superentity.SuperTorder;
import com.kedou.util.CalendarUtil;
import com.kedou.util.Constants;


@Service
@Transactional(readOnly=false)
public class TorderServiceImpl {
	@Resource
	private TorderDaoImpl torderDaoImpl;
	
	
	/**
	 * 创建 SuperTorder 列表(课程 用户 订单)
	 * @param courselist
	 * @param userlist
	 * @param torderlist
	 * @return
	 */
	public List<SuperTorder> createSuperTorder(Map<Integer,Course> coursemap,Map<Integer,User> usermap,List<Torder> torderlist) {
			List<SuperTorder> supertorderlist = new ArrayList<SuperTorder>();
			
			for(int i=0;i<torderlist.size();i++) {
				supertorderlist.add(new SuperTorder(torderlist.get(i), usermap.get(torderlist.get(i).getUserId()), coursemap.get(torderlist.get(i).getCourseId())));
			}
			return supertorderlist;
	}
	/**
	 * 创建 SuperTorder 列表(课程  订单)
	 * @param courselist
	 * @param userlist
	 * @param torderlist
	 * @return
	 */
	public List<SuperTorder> createSuperTorder(Map<Integer,Course> coursemap,List<Torder> torderlist) {
			List<SuperTorder> supertorderlist = new ArrayList<SuperTorder>();
			
			for(int i=0;i<torderlist.size();i++) {
				supertorderlist.add(new SuperTorder(torderlist.get(i),coursemap.get(torderlist.get(i).getCourseId())));
			}
			return supertorderlist;
	}
	/**
	 * 创建  SuperChart(商品销量  图表展示组合类)
	 * @param courseList
	 * @return
	 * @throws Exception 
	 */
	public List<SuperChart> createSuperChartCourseList(List<Course> courseList) throws Exception {
		List<Date> dateList = CalendarUtil.getCurrentWeekDayList();
		List<Integer> courseIdList = new ArrayList<Integer>();
		courseList.forEach((c) -> courseIdList.add(c.getCourseId()));
		
		Map<Integer,SuperChart> superChartMap = new HashMap<Integer,SuperChart>();
		
		for(Date d:dateList) {
			List<Object [] > list = this.torderDaoImpl.findOrderCountByDateCourseIdList(courseIdList, Constants.TORDER_STATE_TREATED, d);
		
	
			for(Course c:courseList) {
				int temp = 0;
				for(int i= 0;i<list.size();i++) {
					if(c.getCourseId()==(int)list.get(i)[0]){
						if(superChartMap.get(c.getCourseId())!=null) {
							SuperChart sc = superChartMap.get(c.getCourseId());
							sc.getOrderNumber().add(Integer.valueOf((list.get(i)[1]).toString()));
						}else {
							List<Integer> num = new ArrayList<Integer>();
							num.add(Integer.valueOf((list.get(i)[1]).toString()));
							superChartMap.put(c.getCourseId(), new SuperChart(c.getCourseName(),num));
						}
						temp++;
					}
				}
				if(temp==0) {
					if(superChartMap.get(c.getCourseId())!=null) {
						SuperChart sc = superChartMap.get(c.getCourseId());
						sc.getOrderNumber().add(0);
					}else {
						List<Integer> num = new ArrayList<Integer>();
						num.add(0);
						superChartMap.put(c.getCourseId(), new SuperChart(c.getCourseName(),num));
					}
				}
			}	
		}
		List<SuperChart> superChartList = new ArrayList<SuperChart>();
			for(Course c:courseList) {
				superChartList.add(superChartMap.get(c.getCourseId()));
				
			}
			
		return superChartList;
	}
	/**
	 * 创建  SuperChart(商品销量  图表展示组合类)
	 * @param courseList
	 * @return
	 * @throws Exception 
	 */
	public SuperChart createSuperChartBusId(int busId) throws Exception {
		List<Date> dateList = CalendarUtil.getCurrentWeekDayList();
		SuperChart superChart = new SuperChart(new ArrayList<Integer>());
		for(Date d:dateList) {
			superChart.getOrderNumberBus().add((int)this.torderDaoImpl.findOrderCountByDateBusId(busId,d));
		}
		
		return superChart;
		
	}
	/**
	 * 保存订单
	 * @param order
	 * @throws Exception 
	 */
	public void SaveTorder(Torder order) throws Exception {
		this.torderDaoImpl.save(order);
	}
	/**
	 * 根据订单Id获取订单实体
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public Torder getTorder(int orderId) throws Exception {
		return this.torderDaoImpl.get(orderId);
	}
	/**
	 * 更改订单状态
	 * @throws Exception 
	 */
	public void updateState(int state,Date processTime,int orderId) throws Exception {
		this.torderDaoImpl.updateState(new Object[] {state,processTime,orderId});
	}
	/**
	 * 查看商家未处理预约列表 （分页）
	 * @param pageNum
	 * @param pageSize
	 * @param busid
	 * @param orderState
	 * @return
	 * @throws Exception
	 */
	public List<Torder> findOrderByBusIdPage(int pageNum,int pageSize,int busid,int orderState) throws Exception {
		return this.torderDaoImpl.findOrderByUserId(busid, pageSize, pageNum,orderState);
	}
	/**
	 * 
	 * @desc 根据商家id查询其预约订单的总数
	 * @author 陈
	 * @param orderState
	 * @param busid
	 * @createDate 
	 * @return count
	 */
	public long findOrderCountByBusId(int busid,int orderState)throws Exception{
		return this.torderDaoImpl.findOrderCountByBusId(busid,orderState);
	}
	/**
	 * 
	 * @desc 根据商家id 和 日期 查询其预约订单的总数
	 * @author 陈
	 * @param orderState
	 * @param busid
	 * @createDate 
	 * @return count
	 */
	public long findOrderCountByDate(int busid,int orderState,Date date)throws Exception{
		return this.torderDaoImpl.findOrderCountByDate(busid, orderState, date);
	}
	/**
	 * @desc 通过用户ID查找预约课程
	 * @author 陈
	 * @createDate 
	 * @return 
	 */
	public List<Torder> findByUserId(int userid)throws Exception  {
		
			return this.torderDaoImpl.findByUserId(userid);
		
	}
	/**
	 * @desc 通过用户ID和课程ID删除预约课程
	 * @author 陈
	 * @createDate 
	 * @return User
	 */
	public void deleteByCourseIdUserId(int courseid,int userid)throws Exception  {
		
			this.torderDaoImpl.deleteByCourseId(courseid,userid);
		
	}
	/**
	 * @desc 通过用户ID查找分页预约课程
	 * @author 陈
	 * @createDate 
	 * @return User
	 */
	public List<Torder> findByUserIdPage(int pageNum, int pageSize,int userid)throws Exception  {
			Integer []params = {userid};
			return this.torderDaoImpl.findByPage(pageNum,pageSize,params);
		
	}
	/**
	 * 
	 * @desc 根据用户id查询其预约课程的总数
	 * @author 陈
	 * @createDate 
	 * @return count
	 */
	public long findCountByUserId(int userid)throws Exception{
		return this.torderDaoImpl.findCountByUserId(userid);
	}
}
