package com.kedou.service.forum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kedou.dao.forum.ForumDaoImpl;
import com.kedou.entity.Answer;
import com.kedou.entity.Article;
import com.kedou.entity.ArticleCollection;
import com.kedou.entity.BusinessNotice;
import com.kedou.entity.Comment;
import com.kedou.entity.HotSpot;
import com.kedou.superentity.SuperArticle;
import com.kedou.superentity.SuperComment;

@Service
@Transactional(readOnly=false)
public class ForumServiceImpl  {
	
	@Resource
	private ForumDaoImpl forumDaoImpl;	
	
	
	
	/**
	 * 添加文章
	 * @param article
	 * @throws Exception
	 */
	public void addArticle(Article article) throws Exception {
		this.forumDaoImpl.saveArticle(article);
	}
	/** 
	 * @desc 论坛轮播图展示
	 * @author 于越明
	 * @createDate 2018年5月22日
	 * @return
	 * @throws Exception
	 */
	public List<Article> findAllPic(){
		return this.forumDaoImpl.findAllPic();
	}
	
	/** 
	 * @desc 论坛热点展示
	 * @author 于越明
	 * @createDate 2018年5月22日
	 * @return
	 * @throws Exception
	 */
	public List<HotSpot> findAllSpot(){
		return this.forumDaoImpl.findAllSpot();
	}
	
	/** 
	 * @desc 论坛文章展示
	 * @author 于越明
	 * @createDate 2018年5月22日
	 * @return
	 * @throws Exception
	 */
	public List<Article> findAllArticle(){
		return this.forumDaoImpl.findAllArticle();
	}
	
	/** 
	 * @desc 论坛推荐文章展示
	 * @author 于越明
	 * @createDate 2018年5月22日
	 * @return
	 * @throws Exception
	 */
	public List<Article> findRecommendArticle(){
		return this.forumDaoImpl.findRecommendArticle();
	}
	
	
	
	/** 
	 * @desc 通过id查询文章
	 * @author 于越明
	 * @createDate 2018年5月22日
	 * @return
	 * @throws Exception
	 */	
	public Article getArticleById(int id){
		return this.forumDaoImpl.getArticleById(id);
	}
	
	/** 
	 * @desc 通过文章id查询文章热点标签
	 * @author 于越明
	 * @createDate 2018年5月22日
	 * @return
	 * @throws Exception
	 */	
	public ArrayList<HotSpot> getHotSpotByArticleId(int id){
		return this.forumDaoImpl.getHotSpotByArticleId(id);
	}
	/** 
	 * @desc 将评论内容存储进入数据库
	 * @author 时自虎
	 * @createDate 2018年5月30日
	 * @return
	 * @throws Exception
	 */
	public void saveComment(Comment comment) throws Exception{
		forumDaoImpl.saveComment(comment);
	}
	
	/**
	 * 
	 * @desc 查询数据库中所有关于此文章的评论
	 * @author 时自虎
	 * @return 
	 * @return 
	 * @createDate 2018年5月31日
	 * @return
	 * @throws Exception
	 */
	public List<Comment> findComment(int articleId)throws Exception{
		return forumDaoImpl.findComment(articleId);
	}
	
	/**
	 * 
	 * @desc 删除一条数据库中关于此文章的评论
	 * @author 时自虎
	 * @return 
	 * @createDate 2018年5月31日
	 * @return
	 * @throws Exception
	 */
	public void deleteComment(int commentId)throws Exception{
		forumDaoImpl.deleteComment(commentId);
	}

	/**
	 * 
	 * @desc 对一条数据库中关于此文章的评论做出回复
	 * @author 时自虎
	 * @return 
	 * @createDate 2018年5月31日
	 * @return
	 * @throws Exception
	 */
	public void answerToComment(Answer answer)throws Exception{
		this.forumDaoImpl.SaveAnswer(answer);
	}
	/**
	 * 
	 * @desc 对一条数据库中关于此文章的评论做出的回复进行遍历
	 * @author 时自虎
	 * @return 
	 * @return 
	 * @createDate 2018年5月31日
	 * @return
	 * @throws Exception
	 */
	public List<Answer> findanswer(int comId)throws Exception{
		return forumDaoImpl.findanswer(comId);
	}
	
	/**
	 * 创建 文章评论和 评论的回复的组合类 List
	 * @param articleid
	 * @return
	 * @throws Exception
	 */
	public List<SuperComment> createSuperComment(int articleid) throws Exception {
		List<Comment> commentList = this.forumDaoImpl.findComment(articleid);
		if(commentList!=null&&commentList.size()!=0) {
			List<Integer> idList = new ArrayList<Integer>();
			for(Comment c:commentList) {
				idList.add(c.getComId());
			}
			
			Map<Integer,List<Answer>> answerMap = this.forumDaoImpl.findMapByCourseList(idList);
			
			List<SuperComment> superCommentList = new ArrayList<SuperComment>();
			for(Comment c :commentList) {
				if(answerMap.get(c.getComId())!=null) {
					superCommentList.add(new SuperComment(c, answerMap.get(c.getComId())));
				}else {
					superCommentList.add(new SuperComment(c, new ArrayList<Answer>()));
				}
			}
			return superCommentList;
		}
		return null;
	}
	/**
	 * 喜欢文章
	 * @param id
	 */
	public void  likeArticle(int id) {
		this.forumDaoImpl.ArticleLikesAddOne(id);
	}
	/**
	 * 不喜欢文章
	 * @param id
	 */
	public void  unlikeArticle(int id) {
		this.forumDaoImpl.UnLikeArticle(id);
	}
	
	/** 
	 * @desc 收藏文章
	 * @author 于越明
	 * @createDate 2018年5月22日
	 * @return
	 * @throws Exception
	 */	
	public void collectArticle(ArticleCollection ac){
		 this.forumDaoImpl.collectArticle(ac);
	}
	/** 
	 * @desc 取消收藏文章
	 * @author 于越明
	 * @createDate 2018年5月22日
	 * @return
	 * @throws Exception
	 */	
	public void uncollectArticle(int articleId,int userId){
		 this.forumDaoImpl.uncollectArticle(articleId,userId);
	}
	
	/**
	 * 查看 一个用户是否收藏文章
	 * @param userid
	 * @param articleId
	 * @return
	 */
	public boolean  ifCollection(int userId,int articleId) {
		
		if(this.forumDaoImpl.findArticleCollection(userId, articleId)!=null){
			return true;
		}
		return false;
	}
	
	public List<SuperArticle> createSuperArticle(List<Article>articleList) {
		List<SuperArticle> superArticleList = new ArrayList<SuperArticle>();
		for(Article a:articleList) {
			superArticleList.add(new SuperArticle(a, (int)this.forumDaoImpl.findCountByArticle(a.getId())));
		}
		return superArticleList;
	}
}
