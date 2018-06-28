package com.kedou.dao.forum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kedou.dao.framework.BaseDao;
import com.kedou.entity.Answer;
import com.kedou.entity.Article;
import com.kedou.entity.ArticleCollection;
import com.kedou.entity.Collection;
import com.kedou.entity.Comment;
import com.kedou.entity.HotSpot;

@Repository
public class ForumDaoImpl {
	@Resource
	private SessionFactory sessionFactory;
	
	
	/**
	 * 新增文章
	 * @author 张天润
	 * @param article
	 */
	public void saveArticle(Article article) {
		this.sessionFactory.getCurrentSession().save(article);
	}
	
	/** 
	 * @desc 论坛轮播图展示
	 * @author 于越明
	 * @createDate 2018年5月22日
	 * @return
	 * @throws Exception
	 */
	public List<Article> findAllPic(){
		List<Article> forumpic = new ArrayList<Article>();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select f from Article f where f.likes > 1000");
		forumpic = query.list();
		return forumpic;
	}
	
	/** 
	 * @desc 论坛热点展示
	 * @author 于越明
	 * @createDate 2018年5月22日
	 * @return
	 * @throws Exception
	 */
	public List<HotSpot> findAllSpot(){
		List<HotSpot> HotSpot = new ArrayList<HotSpot>();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from HotSpot");
		HotSpot = query.list();
		return HotSpot;
	}
	
	/** 
	 * @desc 论坛文章展示
	 * @author 于越明
	 * @createDate 2018年5月22日
	 * @return
	 * @throws Exception
	 */
	public List<Article> findAllArticle(){
		List<Article> Article = new ArrayList<Article>();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Article");
		Article = query.list();
		return Article;
	}
	
	/** 
	 * @desc 论坛推荐文章展示
	 * @author 于越明
	 * @createDate 2018年5月22日
	 * @return
	 * @throws Exception
	 */
	public List<Article> findRecommendArticle(){
		List<Article> forumpic = new ArrayList<Article>();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select f from Article f where f.likes > 200");
		forumpic = query.list();
		return forumpic;
	}
	
	/** 
	 * @desc 收藏文章
	 * @author 于越明
	 * @createDate 2018年5月22日
	 * @return
	 * @throws Exception
	 */	
	public void collectArticle(ArticleCollection ac){
		this.sessionFactory.getCurrentSession().save(ac);
	}
	/** 
	 * @desc 取消收藏文章
	 * @author 于越明
	 * @createDate 2018年5月22日
	 * @return
	 * @throws Exception
	 */	
	public void uncollectArticle(int articleId,int userId){
		String hql = "delete ArticleCollection where articleId = :articleid and userId = :userid";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("articleid", articleId);
		query.setParameter("userid", userId);
		
		query.executeUpdate();
	}
	
	/** 
	 * @desc 通过id查询文章
	 * @author 于越明
	 * @createDate 2018年5月22日
	 * @return
	 * @throws Exception
	 */	
	public Article getArticleById(int id){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select a from Article a where a.id=?");
		query.setParameter(0,id);
		Article a = (Article)query.uniqueResult();
		return a;
	}
	
	/** 
	 * @desc 通过文章id查询文章热点标签
	 * @author 于越明
	 * @createDate 2018年5月22日
	 * @return
	 * @throws Exception
	 */	
	public ArrayList<HotSpot> getHotSpotByArticleId(int id){
		ArrayList<HotSpot> hotspots = new ArrayList<HotSpot>();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select a from Article a where a.id=?");
		query.setParameter(0,id);
		Article a = (Article)query.uniqueResult();
		String hotspot = a.getHotspots();
		System.out.println(hotspot);
		String [] result = hotspot.split(",");
		for(String p:result){
			int spot = Integer.parseInt(p);
			Query query1 = session.createQuery("select h from HotSpot h where h.id=?");
			query1.setParameter(0,spot);
			HotSpot s = (HotSpot)query1.uniqueResult();
			hotspots.add(s);
		}
		return hotspots;
		
	}
	
	/**
	 * 
	 * @desc 将评论内容存储进入数据库
	 * @author 时自虎
	 * @createDate 2018年5月30日
	 * @return
	 * @throws Exception
	 */
	public void saveComment(Comment comment)throws Exception{
		this.sessionFactory.getCurrentSession().save(comment);
	}
	/**
	 * 
	 * @desc 查询数据库中所有关于此文章的评论
	 * @author 时自虎
	 * @return 
	 * @createDate 2018年5月31日
	 * @return
	 * @throws Exception
	 */
	public List<Comment> findComment(int articleId)throws Exception{
		List<Comment> allcomment = new ArrayList<Comment>();
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("select c from Comment c where c.articleId = ?");
		query.setParameter(0, articleId);
		allcomment = query.list();
		
		session.close();
		
		return allcomment;
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
		Session session = sessionFactory.openSession();
		Transaction ts = session.beginTransaction();
		Query query = session.createQuery("select c from Comment c where c.comId = ?");
		query.setParameter(0, commentId);
		Comment delc = (Comment) query.uniqueResult();
		session.delete(delc);
		
		ts.commit();
		session.close();
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
	public void SaveAnswer(Answer answer)throws Exception{
		this.sessionFactory.getCurrentSession().save(answer);
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
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("select a from Answer a where a.comId = ?");
		query.setParameter(0, comId);
		List<Answer> allanswer = query.list();
		
		session.close();
		
		return allanswer;
	}
	
	public Map<Integer,List<Answer>> findMapByCourseList(List<Integer> comIdList) {
		String hql = "from Answer where comId in (:idList) ";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
			query.setParameterList("idList", comIdList);
			 List<Answer> answerList = query.list();
			 
			 Map<Integer,List<Answer>> answerMap = new HashMap<Integer,List<Answer>>();
			 if(answerList !=null&&answerList.size()!=0) {
				 for(int id :comIdList) {
					 List<Answer> comAnswerList = new ArrayList<Answer>();
					 for(Answer a:answerList) {
						if(a.getComId()==id) {
							comAnswerList.add(a);
						}
					 }
					 answerMap.put(id, comAnswerList);
				 }
				
			 }
			 return  answerMap ;
	}
	
	public void  ArticleLikesAddOne(int id) {
		String hql = "update Article set likes = (likes + 1) where id = ?";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, id);
		query.executeUpdate();	
	}
	public void  UnLikeArticle(int id) {
		String hql = "update Article set likes = (likes - 1) where id = ?";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, id);
		query.executeUpdate();
	}
	/**
	 * 查看 一个用户是否收藏文章
	 * @param userid
	 * @param articleId
	 * @return
	 */
	public ArticleCollection  findArticleCollection(int userId,int articleId) {
		String hql ="from ArticleCollection where articleId =:articleId and userId = :userId";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("articleId", articleId);
		query.setParameter("userId", userId);
		
		return (ArticleCollection)query.uniqueResult();
	}
	
	public long findCountByArticle(int articleId) {
		String hql="select count(*) from ArticleCollection where articleId = :articleid";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("articleid", articleId);
		
		return (long)query.uniqueResult();
	}
}
