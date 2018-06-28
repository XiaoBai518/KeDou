package com.kedou.controller.forum;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.kedou.entity.Answer;
import com.kedou.entity.Article;
import com.kedou.entity.ArticleCollection;
import com.kedou.entity.BusinessVisitNumber;
import com.kedou.entity.Comment;
import com.kedou.entity.HotSpot;
import com.kedou.entity.User;
import com.kedou.service.forum.ForumServiceImpl;
import com.kedou.superentity.SuperArticle;
import com.kedou.superentity.SuperComment;
import com.kedou.util.Constants;
import com.kedou.util.UpLoadErro;
import com.kedou.util.UpLoadUtil;


@Controller
@RequestMapping("/forum")
public class ForumController {
	@Resource
	private ForumServiceImpl forumServiceImpl;
	
	
	@Resource(name="springcacheManager")
	private EhCacheCacheManager CacheManager;

	/** 
	 * @desc 显示论坛首页
	 * @author 于越明
	 * @createDate 2018年5月22日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/showforum",method=RequestMethod.GET)
	public String showForum(Model model){
		
		List<Article> forumpic = forumServiceImpl.findAllPic();//得到轮播列表
		for(int i=0; i<forumpic.size();i++)
		model.addAttribute("piclist",forumpic);
		
		List<HotSpot> hotspot = forumServiceImpl.findAllSpot();//得到热点文字列表
		model.addAttribute("hotspotlist",hotspot);
		
		
		List<Article> articleList = forumServiceImpl.findAllArticle();//得到文章列表
		
		List<SuperArticle> superArticleList = this.forumServiceImpl.createSuperArticle(articleList);
		model.addAttribute("superArticlelist",superArticleList);
		
	
		
		List<Article> recomlist = forumServiceImpl.findRecommendArticle();//得到推荐文章列表
		model.addAttribute("relist",recomlist);
		
		return "forum-index";
	}
	/**
	 * 公告图片上传（ZUI框架）
	 * @param file
	 * @return
	 */
	@RequestMapping(value="/uploadImg",method=RequestMethod.POST,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String uploadImg(@RequestParam("titleImg") MultipartFile file) {
	
		//写入文件
    		String uploadResult = UpLoadUtil.uploadImg(file, "article");
    			if(!"-1".equals(uploadResult)) {
    				return new Gson().toJson(uploadResult);
    			}else {
    				
    				//写入文件出错   报告错误 （只适用ZUI框架）
    					UpLoadErro e = new UpLoadErro();
    					e.setResult(false);
    					e.setMessage("写入文件错误");
    				return new Gson().toJson(e);
    	}
	}
	/**
	 * 商家新增文章
	 * @return
	 */
	@RequestMapping(value="busaddArticle",method=RequestMethod.POST)
	public String addArticle(Article article) {
		Date createTime = new Date();
//		//时间格式化工具
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		try {
			//设置公告创建时间
			article.setDates(createTime);
			this.forumServiceImpl.addArticle(article);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "bus_addArticle";
	}
	/**
	 * 前往商家添加文章
	 * @return
	 */
	@RequestMapping(value="/tobusaddArticle",method=RequestMethod.GET)
	public String toaddNotice() {
		
		return "bus_addArticle";
	}
	/**
	 * 前往用户写文章界面
	 * @return
	 */
	@RequestMapping("/touseraddArticle")
	public String view(){
		return "user_addArticle";
	}
	/**
	 * 用户预览文章
	 * @param session
	 * @param info
	 * @param file
	 * @param title
	 * @param content
	 * @return
	 */
	@RequestMapping(value="/preview",method=RequestMethod.POST)
	 public String Preview(Model model,
			 @RequestParam("title")String title,@RequestParam("content")String content,
			 @RequestParam(value="info",required=false)String info,@RequestParam(value="Img",required=false)MultipartFile file) {
		if (file!=null&&!file.isEmpty()) {
			String uploadResult = UpLoadUtil.uploadImg(file, "articleTemp");
			if(!"-1".equals(uploadResult)) {
				Article article = new Article();
				article.setContent(content);
				article.setTitle(title);
				article.setTitleimg(uploadResult);
				model.addAttribute("article", article);
				
				return "articlePreview";
			}else {
				//写入文件出错
				model.addAttribute("error", "UploadError");
				return "user_addArticle";
			}
    }else{
   	 if(info==""){
   		 model.addAttribute("error", "NoImg");
   		 return "user_addArticle";
   	 }else{
   		Article article = new Article();
		article.setContent(content);
		article.setTitle(title);
   		 article.setTitleimg(info);
   		model.addAttribute("article", article);
		return "articlePreview";
   	 }
    }
	}
	/**
	 * 退出预览
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/endpreview",method=RequestMethod.POST)
	 public String Preview(	 @RequestParam("title")String title,
			 				@RequestParam("content")String content,
			 				@RequestParam(value="img")String img,Model model){
		model.addAttribute("title", title);
		model.addAttribute("content", content);
		model.addAttribute("img", img);
		System.out.println("123");
		 return "user_addArticle";
	 }
	/**
	 * 用户新增文章
	 * @param session
	 * @param info
	 * @param title
	 * @param content
	 * @param file
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/adduserarticle",method=RequestMethod.POST)
	public String toAdd( @RequestParam("title")String title,@RequestParam("content")String content,@RequestParam(value="info",required=false)String info,
								@RequestParam(value="Img",required=false)MultipartFile file,
								Model model){
		
		if (!file.isEmpty()) {
			String uploadResult = UpLoadUtil.uploadImg(file, "article");
			if(!"-1".equals(uploadResult)) {
				Article article = new Article();
				article.setContent(content);
				article.setTitle(title);
				//设置标题图片的路径
				 article.setTitleimg(uploadResult);
				 //设置 创建时间
					article.setDates(new Date()); 
				 	
					//设置作者
					article.setAuthor(((User)SecurityUtils.getSubject().getSession().getAttribute("loginUser")).getUserName());
					//写入数据库
					  try {
						this.forumServiceImpl.addArticle(article);
					} catch (Exception e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
			}
			return "forum-index";
		}else{
    	 if(info==""){
    		 //用户没有上传图片
    		 model.addAttribute("error", "NoImg");
    			return "user_addArticle";
    	 }else{
    		 //将文件复制到Article文件夹下
    		 	UpLoadUtil.copyFile(new File(Constants.UPLOADURL_TEMP+ File.separator + info), new File(Constants.UPLOADURL_ARTICLE+ File.separator + info));
    			Article article = new Article();
				article.setContent(content);
				article.setTitle(title);
    		 	//设置标题图片的路径
			 article.setTitleimg(info);
			 //设置 创建时间
				article.setDates(new Date()); 
				//设置作者
				article.setAuthor(((User)SecurityUtils.getSubject().getSession().getAttribute("loginUser")).getUserName());
				//写入数据库
				  try {
					this.forumServiceImpl.addArticle(article);
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
    		 return "forum-index";
    	 }
     }	
	}
	
	
	
	/** 
	 * @desc 文章详情展示
	 * @author 于越明
	 * @createDate 2018年5月22日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/details",method=RequestMethod.GET)
	public String details(@RequestParam("keyid") Integer id,HttpSession session,Model model){
		//查询相应的文章 并且设置阅读数量
		Article a = this.forumServiceImpl.getArticleById(id);
		
		Cache cache =CacheManager.getCache("articleReadNumberCache");
		ValueWrapper value = cache.get("readNumberMap");
		if(value!=null) {
			Map<Integer,Integer> readNumberMap = (Map<Integer,Integer>)value.get();
			a.setViews(readNumberMap.get(a.getId()));
		}
		model.addAttribute("artdetail", a);
	
		//查看文章是否被用户收藏
		User user = (User)SecurityUtils.getSubject().getSession().getAttribute("loginUser");
		if(this.forumServiceImpl.ifCollection(user.getUserId(), a.getId())) {
			model.addAttribute("ifCollection", "true");
		}else {
			model.addAttribute("ifCollection", "false");
		}
		
		
		 try {
			 List<SuperComment> c = this.forumServiceImpl.createSuperComment(a.getId());
			model.addAttribute("superComment", c);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		 List<Article> recommendArticle = new ArrayList<Article>();
		 String [] imgPath = {"20180626223616.jpg","20180626223915.jpg","20180626225032.jpg"};
		 String [] title = {"考研，不只是学习，还有心态","四六级内容区别与备考要点大总结","四级刚过如何考到雅思8分：烤鸭二战拿下8分心得"};
		 int [] articleid = {5,6,8};
		 for(int i=0;i<3;i++) {
			 Article art = new Article();
			 art.setId(articleid[i]);
			 art.setTitleimg(imgPath[i]);
			 art.setTitle(title[i]);
			 recommendArticle.add(art);
		 }
		 model.addAttribute("recommendArticle", recommendArticle);
		return "forum-article";
		
	}
	
	/**
	 * @desc 将评论内容存储进入数据库
	 * @author 时自虎
	 * @createDate 2018年5月30日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/savecomments", method=RequestMethod.POST,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String saveComment(Comment comment,@RequestParam("time")String time,Model model)throws Exception{
		User user = (User)SecurityUtils.getSubject().getSession().getAttribute("loginUser");
		
		//设置 评论的 评论者姓名 评论者Id 评论者头像
		comment.setUserName(user.getUserName());
		comment.setUserId(user.getUserId());
		comment.setUserIcon(user.getUserIcon());
		//时间格式化工具
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        //设置评论的时间
		comment.setPublicTime(simpleDateFormat.parse(time));
		
		forumServiceImpl.saveComment(comment);
		return new Gson().toJson(user);
	}
	
	/**
	 * 
	 * @desc 查询数据库中所有关于此文章的评论
	 * @author 时自虎
	 * @return 
	 * @return 
	 * @return 
	 * @createDate 2018年5月31日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/findcomments", method=RequestMethod.GET)
	public String findComment(HttpSession session)throws Exception{
		//Object article = session.getAttribute("artdetail");
		Article art = new Article();
		art.setId(0);
		session.setAttribute("somecomments",forumServiceImpl.findComment(art.getId())); 
		
		Comment c = new Comment();
		c.setComId(1);
		session.setAttribute("someanswers", forumServiceImpl.findanswer(c.getComId()));
		 
		return "comment";
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
	@RequestMapping(value="/answertocomments", method=RequestMethod.POST,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String answerToComment(Answer answer,@RequestParam("time")String time)throws Exception{
		User user = (User)SecurityUtils.getSubject().getSession().getAttribute("loginUser");
		System.out.println("回复内容"+answer.getAnswers());
		
		
		//设置 评论的 评论者姓名 评论者Id 评论者头像
		answer.setUserName(user.getUserName());
		answer.setUserId(user.getUserId());
		answer.setUserIcon(user.getUserIcon());
		//时间格式化工具
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        //设置评论的时间
        answer.setPublicTime(simpleDateFormat.parse(time));
        
        
		forumServiceImpl.answerToComment(answer);
		return new Gson().toJson(user);
	}
	/**
	 * 文章的喜欢
	 * @return
	 */
	@RequestMapping(value="likeArticle",method=RequestMethod.GET) 
	@ResponseBody
	public String like (@RequestParam("articleId")int id) {
		
		this.forumServiceImpl.likeArticle(id);
		SecurityUtils.getSubject().getSession().setAttribute("articleLike", id);
		return "success";
	}
	/**
	 * 文章取消喜欢
	 * @return
	 */
	@RequestMapping(value="unlikeArticle",method=RequestMethod.GET) 
	@ResponseBody
	public String unlike (@RequestParam("articleId")int id) {
		
		this.forumServiceImpl.unlikeArticle(id);
		SecurityUtils.getSubject().getSession().removeAttribute("articleLike");
		return "success";
	}
	/** 
	 * @desc 收藏文章
	 * @author 于越明
	 * @createDate 2018年5月22日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/collect",method=RequestMethod.GET)
	@ResponseBody
	public String collectArticle(@RequestParam("articleId")int id){

		User user = (User)SecurityUtils.getSubject().getSession().getAttribute("loginUser");
		this.forumServiceImpl.collectArticle(new ArticleCollection(id,user.getUserId()));

		return "success";	
	}
	/** 
	 * @desc 取消收藏文章
	 * @author 于越明
	 * @createDate 2018年5月22日
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/uncollect",method=RequestMethod.GET)
	@ResponseBody
	public String uncollectArticle(@RequestParam("articleId")int id){

		User user = (User)SecurityUtils.getSubject().getSession().getAttribute("loginUser");
		this.forumServiceImpl.uncollectArticle(id,user.getUserId());

		return "success";	
	}
	
	
}


