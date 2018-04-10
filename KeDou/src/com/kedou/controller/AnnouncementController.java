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

import com.kedou.entity.Announcement;
import com.kedou.entity.Business;
import com.kedou.service.AnnouncementService;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController {
	@Resource
	private AnnouncementService announcementService;

	public AnnouncementService getAnnouncementService() {
		return announcementService;
	}

	public void setAnnouncementService(AnnouncementService announcementService) {
		this.announcementService = announcementService;
	}
	
	//插入公告
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@RequestParam("accTitle") String accTitle,
					@RequestParam("accContent") String accContent,
					HttpServletRequest request){
		String busId = request.getParameter("busId");
		
		announcementService.save(accTitle,accContent,Integer.parseInt(busId));
		return "index";
	}
	//查询公告
	@RequestMapping(value="/getAll",method=RequestMethod.GET)
	public String getAll(HttpSession session){
		List<Announcement> list = announcementService.find();
		session.setAttribute("acclist", list);
		return "acclist";
	}
	
	//to修改公告
	@RequestMapping(value="/toedit",method=RequestMethod.GET)
	public String toedit(@RequestParam("accId") int accId,
						HttpSession session){
		Announcement announcement = announcementService.toedit(accId);
		session.setAttribute("acc", announcement);
		return "editacc";
	}
	
	//真正修改公告
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public String edit(@RequestParam("accId") int accId,
					@RequestParam("busId") int busId,
					@RequestParam("accTitle") String accTitle,
					@RequestParam("accContent") String accContent,
					@RequestParam("accPublish") String accPublish,
					@RequestParam("accRead") int accRead){
		Announcement announcement = new Announcement();
		Business business = new Business();
		announcement.setAccId(accId);
		announcement.setAccTitle(accTitle);
		announcement.setAccContent(accContent);
		announcement.setAccRead(accRead);
		Announcement ann = announcementService.findById(accId);
		String publish = ann.getAccPublish();
		if(publish.equals(accPublish)){
			Date accPublish1 = new Date();    
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			announcement.setAccPublish(df.format(accPublish1));
		}else{
			announcement.setAccPublish(accPublish);
		}		
		business.setBusId(busId);
		announcementService.edit(accId, announcement);
		return "forward:getAll";
	}
	//删除公告
	@RequestMapping(value="/del",method=RequestMethod.GET)
	public String del(@RequestParam("accId") int accId){
		announcementService.del(accId);
		return "forward:getAll";
	}
	//更新公告访问次数
	/*public String update(@RequestParam("accId") int accId){
		
	}*/
	
}
