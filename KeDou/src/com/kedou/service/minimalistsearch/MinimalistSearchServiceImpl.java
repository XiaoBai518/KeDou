package com.kedou.service.minimalistsearch;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.dao.minimalistsearch.MinimalistSearchDaoImpl;
import com.kedou.entity.ChooseAnswer;
import com.kedou.entity.ChooseQuestion;

@Service
@Transactional(readOnly=false)
public class MinimalistSearchServiceImpl {
	@Resource
	private MinimalistSearchDaoImpl chooseDaoImpl;
	/**
	 * 
	 * @desc 通过选择问题查找答案
	 */
	public ChooseQuestion findByQuestion(String question)throws Exception  {
			return this.chooseDaoImpl.findByQuestion(question);
	}
	public List<ChooseAnswer> findByAnswer(int questionid)throws Exception  {
		return this.chooseDaoImpl.findByAnswer(questionid);
}

}
