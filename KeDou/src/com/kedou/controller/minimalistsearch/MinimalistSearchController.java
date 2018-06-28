package com.kedou.controller.minimalistsearch;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import com.kedou.entity.ChooseQuestion;
import com.kedou.service.minimalistsearch.MinimalistSearchServiceImpl;
import com.kedou.service.search.SearchServiceImpl;
import com.kedou.util.Constants;


@Controller
@RequestMapping("/minimalist")
public class MinimalistSearchController {
	@Resource
	private MinimalistSearchServiceImpl minimalistSearchServiceImpl;
	
	
	/**
	 * 前往极简搜索界面
	 * @return
	 */
	@RequestMapping(value="tochoose",method=RequestMethod.GET)
	public String switchingMode(@RequestParam("search")String search,Model model) {
		if(search.contains("考研")) {
			try {
				ChooseQuestion  chooseQuestion = this.minimalistSearchServiceImpl.findQuestionById(Constants.MINIMALISTSEARCH_KAOYAN);
				model.addAttribute("question", chooseQuestion);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(search.contains("四六级")||search.contains("四级")||search.contains("六级")) {
			try {
				ChooseQuestion  chooseQuestion = this.minimalistSearchServiceImpl.findQuestionById(Constants.MINIMALISTSEARCH_SILIUJI);
				model.addAttribute("question", chooseQuestion);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("search", search);
			return "minimalistSearch";
	}
	
	/**
	 * 切换页面
	 * @param question
	 * @return
	 */
	@RequestMapping(value="choosenext",method=RequestMethod.GET,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String choosetext(@RequestParam("id") int questionId) {
		try {
			ChooseQuestion question = this.minimalistSearchServiceImpl.findNextQuestionById(questionId);
			
				return new  Gson().toJson(question);
			
			
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return "数据库错误界面";
		}

		}

	
}
