package com.kedou.service.forum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.dao.forum.ArticleDaoImpl;
import com.kedou.entity.Article;

@Service
@Transactional(readOnly=false,rollbackFor=RuntimeException.class)
public class ArticleServiceImpl {

	@Resource
	private ArticleDaoImpl articleDaoImpl;
	
	
	public Map<Integer,Integer> initMap() throws Exception {
		//获取全部的课程
		List<Article> articleList = this.articleDaoImpl.findAll();
		Map<Integer,Integer> readNumberMap = new HashMap<Integer, Integer>();
		if(articleList!=null&&articleList.size()!=0) {
			for(Article a:articleList) {
				readNumberMap.put(a.getId(), a.getViews());
			}
		}
		
		return readNumberMap;
	}
}
