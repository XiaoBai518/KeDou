package com.kedou.service.user.collection;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.dao.user.collection.CollectionDaoImpl;
import com.kedou.entity.Collection;

@Service
@Transactional(readOnly=false)
public class CollectionServiceImpl {

	@Resource
	private CollectionDaoImpl collectionDaoImpl;
	
	/**
	 * @desc 通过用户ID查找收藏课程
	 * @author 陈
	 * @createDate 
	 * @return 
	 */
	public List<Collection> findCollectionByUserId(int userid)throws Exception  {
		
			return this.collectionDaoImpl.findCollectionByUserId(userid);
		
	}
	

	/**
	 * @desc 通过用户ID和课程ID删除收藏课程
	 * @author 陈
	 * @createDate 
	 * @return User
	 */
	public void deleteCollectionByCourseId(int courseid,int userid)throws Exception  {
		
			this.collectionDaoImpl.deleteCollectionByCourseId(courseid,userid);
		
	}
	
	
	/**
	 * @desc 通过用户ID分页查找收藏课程
	 * @author 陈
	 * @createDate 
	 * @return User
	 */
	public List<Collection> findByPage(int pageNum, int pageSize,int userid)throws Exception  {
		
		Integer [] params = {userid};
			return this.collectionDaoImpl.findByPage(pageNum, pageSize, params);
		
	}


	/**
	 * 
	 * @desc 根据用户id查询其收藏课程的总数
	 * @author 陈
	 * @createDate 
	 * @return count
	 */
	public long findCollectionCount(int userid)throws Exception{
		return this.collectionDaoImpl.findCollectCount(userid);
	}
}
