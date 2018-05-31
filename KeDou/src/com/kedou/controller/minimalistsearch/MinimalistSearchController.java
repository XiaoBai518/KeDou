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
import com.kedou.entity.ChooseAnswer;
import com.kedou.entity.ChooseQuestion;
import com.kedou.service.minimalistsearch.MinimalistSearchServiceImpl;


@Controller
@RequestMapping("/minimalist")
public class MinimalistSearchController {
	@Resource
	private MinimalistSearchServiceImpl chooseServiceImpl;
	
	/**
	 * 前往极简搜索界面
	 * @return
	 */
	@RequestMapping(value="tochoose",method=RequestMethod.GET)
	public String switchingMode(@RequestParam("search")String search,Model model) {
		
		model.addAttribute("search", search);
			return "minimalistSearch";
	}
	
	@RequestMapping(value="choosetext",method=RequestMethod.GET,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String choosetext(@RequestParam("name") String question) {
		ChooseQuestion qus;
		List<ChooseAnswer> ans = null;
		try {
			qus = this.chooseServiceImpl.findByQuestion(question);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return "数据库错误界面";
		}
		if(qus==null){
			System.out.println("qus为空");
			Gson json = new  Gson();
			if(ans==null){
				System.out.println("ans为空");
			}
			return json.toJson(ans);
			}else{
				try {
					ans = this.chooseServiceImpl.findByAnswer(qus.getId());	
					} catch (Exception e) {
						// TODO 自动生成的 catch 块
							e.printStackTrace();
							return "数据库错误界面";
					}
					Gson json = new  Gson();										
					return json.toJson(ans);				

			}

		}

	
}
