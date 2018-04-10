package com.kedou.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kedou.dao.ArticleDao;
import com.kedou.entity.Article;

@Service
public class ArticleService {
	@Resource
	private ArticleDao articleDao;

	public ArticleDao getArticleDao() {
		return articleDao;
	}

	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}
	//添加推荐文章
	public void save(Article article,int userId){
		articleDao.insert(article,userId);
	}
	//to修改文章
	public Article toedit(int artId){
		return articleDao.toedit(artId);
	}
	//真正修改
	public void edit(int artId,Article article){
		articleDao.edit(artId,article);
	}
	//删除文章
	public void del(int artId){
		articleDao.del(artId);
	}
	//查询所有文章
	public List<Article> findAll(){
		return articleDao.findAll();
	}
}
