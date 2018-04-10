package com.kedou.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kedou.entity.Article;
import com.kedou.entity.User;
import com.kedou.service.ArticleService;

@Controller
@RequestMapping("/article")
public class ArticleController {
	@Resource
	private ArticleService articleService;

	public ArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}
	//添加推荐
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@RequestParam("title") String title,
			@RequestParam("content") String content,
			HttpServletRequest request){
		
		String userId = request.getParameter("userId");
		Article article = new Article();
		article.setTitle(title);
		article.setContent(content);
		Date Publish1 = new Date();    
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		article.setPublish(df.format(Publish1));
		article.setUserId(Integer.parseInt(userId));
		articleService.save(article, new Integer(userId));
		return "insertart";
	}
	//to修改文章
	@RequestMapping(value="/toedit",method=RequestMethod.GET)
	public String toedit(@RequestParam("artId") int artId,
						HttpSession session){
		Article article= articleService.toedit(artId);
		session.setAttribute("art", article);
		return "artedit";
	}
	//修改文章
	@RequestMapping(value="/edit",method=RequestMethod.GET)
	public String edit(@RequestParam("artId") int artId,
						@RequestParam("title") String title,
						@RequestParam("content") String content,
						@RequestParam("publish") String publish,
						@RequestParam("userId") int userId){
		Article article = new Article();
		article.setArtId(artId);
		article.setTitle(title);
		article.setContent(content);
		article.setPublish(publish);
		article.setUserId(userId);
		articleService.edit(artId,article);
		return "forward:findAll";
	}
	//删除文章
	@RequestMapping(value="/del",method=RequestMethod.GET)
	public String del(@RequestParam("artId") int artId){
		articleService.del(artId);
		return "forward:findAll";
	}
	//查询所有推荐文章
	@RequestMapping(value="/findAll",method=RequestMethod.GET)
	public String findAll(HttpSession session){
		List<Article> list = articleService.findAll();
		session.setAttribute("artlist", list);
		return "artlist";
	}
}
