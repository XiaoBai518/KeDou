package com.kedou.service.minimalistsearch;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.dao.minimalistsearch.MinimalistSearchDaoImpl;
import com.kedou.entity.ChooseQuestion;

@Service
@Transactional(readOnly=false)
public class MinimalistSearchServiceImpl {
	@Resource
	private MinimalistSearchDaoImpl minimalistSearchDaoImpl;
	
	/**
	 * 通过问题Id查找问题实体
	 * @param questionId
	 * @return
	 * @throws Exception
	 */
	public ChooseQuestion findQuestionById(int questionId) throws Exception {
		
		 return  this.minimalistSearchDaoImpl.get(questionId);
		 
	}
	/**
	 * 通过问题Id查找下一个问题
	 * @param questionId
	 * @return
	 * @throws Exception
	 */
	public ChooseQuestion findNextQuestionById(int questionId) throws Exception {
		
		int id = this.minimalistSearchDaoImpl.findNextId(questionId);
		if(id!=0) {
			 return  this.minimalistSearchDaoImpl.get(id);
		}else {
			return null;
		}
		
		 
	}

}
