package com.kedou.service.user.history;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.dao.user.history.HistoryDaoImpl;
import com.kedou.entity.History;

@Service
@Transactional(readOnly=false)
public class HistoryServiceImpl {
	@Resource
	private HistoryDaoImpl historyDaoImpl;

	/**
	 * @desc 通过用户ID查找访问历史课程
	 * @author 陈
	 * @createDate 
	 * @return 
	 */
	public List<History> findHistoryByUserId(int userid)throws Exception  {
		
			return this.historyDaoImpl.findHistoryByUserId(userid);
		
	}
	/**
	 * @desc 通过用户ID和课程ID删除访问历史课程
	 * @author 陈
	 * @createDate 
	 * @return User
	 */
	public void deleteHistoryByCourseId(int courseid,int userid)throws Exception  {
		
			this.historyDaoImpl.deleteHistoryByCourseId(courseid,userid);
		
	}
	/**
	 * @desc 通过用户ID查找分页访问历史课程
	 * @author 陈
	 * @createDate 
	 * @return User
	 */
	public List<History> findByPageHistory(int pageNum, int pageSize,int userid)throws Exception  {
			Integer [] params ={userid};
			return this.historyDaoImpl.findByPageHistory(pageNum,pageSize,params);
		
	}
	/**
	 * 
	 * @desc 根据用户id查询其访问历史课程的总数
	 * @author 陈
	 * @createDate 
	 * @return count
	 */
	public long findHistoryCount(int userid)throws Exception{
		return this.historyDaoImpl.findCountByUserId(userid);
	}
}
