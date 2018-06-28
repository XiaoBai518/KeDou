package com.kedou.controller.business.Notice;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.kedou.entity.Business;
import com.kedou.entity.BusinessNotice;
import com.kedou.service.business.notice.NoticeServiceImpl;
import com.kedou.util.UpLoadErro;
import com.kedou.util.UpLoadUtil;

@Controller
@RequestMapping("/businessnotice")
public class NoticeController {

	@Resource
	private NoticeServiceImpl noticeServiceImpl;
	
	/**
	 * 前往添加公告页
	 * @return
	 */
	@RequestMapping(value="/toaddNotice",method=RequestMethod.GET)
	public String toaddNotice() {
		
		return "bus_addNotice";
	}
	/**
	 * 添加公告
	 * @param notice
	 * @return
	 */
	@RequestMapping(value="/addNotice",method=RequestMethod.POST)
	public String addNotice(BusinessNotice notice,@RequestParam("createtime")String createTime) {
		//时间格式化工具
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		try {
			//设置公告创建时间
			notice.setCreateTime(simpleDateFormat.parse(createTime));
			this.noticeServiceImpl.addNotice(notice);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "bus_addNotice";
	}
	
	/**
	 * 公告图片上传
	 * @param file
	 * @return
	 */
	@RequestMapping(value="/uploadImg",method=RequestMethod.POST,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String uploadImg(@RequestParam("noticeImg") MultipartFile file) {
	
    		String uploadResult = UpLoadUtil.uploadImg(file, "notice");
    			if(!"-1".equals(uploadResult)) {
    				return new Gson().toJson(uploadResult);
    			}else {
			UpLoadErro e = new UpLoadErro();
			e.setResult(false);
			e.setMessage("写入文件错误");
    				return new Gson().toJson(e);
    			}
	}
	/**
	 * 前往 查看公告列表页
	 * @param busid
	 * @return
	 */
	@RequestMapping(value="/toNoticeList",method=RequestMethod.GET)
	public String getNoticeList(Model model) {
		Business bus = (Business)SecurityUtils.getSubject().getSession().getAttribute("loginBusiness");
		int busid = bus.getBusId();
		List<BusinessNotice> noticelist = this.noticeServiceImpl.findNoticeByBusid(busid);
		
		model.addAttribute("noticeList", noticelist);
		return "bus_noticeList";
	}
}
