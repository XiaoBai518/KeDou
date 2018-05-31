package com.kedou.service.torder;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.dao.torder.TorderDaoImpl;
import com.kedou.entity.Torder;


@Service
@Transactional(readOnly=false)
public class TorderServiceImpl {
	@Resource
	private TorderDaoImpl torderDaoImpl;
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
	public List<Torder> findByPage(int pageNum, int pageSize,int userid)throws Exception  {
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
