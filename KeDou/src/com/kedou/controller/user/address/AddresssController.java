package com.kedou.controller.user.address;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kedou.entity.Address;
import com.kedou.entity.User;
import com.kedou.service.user.address.AddressServiceImpl;
import com.kedou.util.Constants;
import com.kedou.util.SessionUtil;

@Controller
@RequestMapping("/useraddress")
public class AddresssController {
	@Resource
	private AddressServiceImpl addressServiceImpl;
	
	/**
	 * 前往用户地址选择界面
	 * @return
	 */
	@RequestMapping(value="/toaddress",method=RequestMethod.GET)
	public String touseraddress() {
		return "useraddress";
	}
	/**
	 * 按省 /市 /区 选择
	 * @param city
	 * @param area
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(@RequestParam("city") String city,@RequestParam("area")String area,
						HttpSession session,HttpServletResponse response){
		Address address = new Address();
		address.setCity(city);
		address.setArea(area);
		address.setAddress();
		
		SessionUtil.toSession("userAddress", address, "userAddressC", address, Constants.COOKIE_SAVETIME, response, session);
		return "index";
	}
	/**
	 * 热门城市
	 * @param session
	 * @param cityarea
	 * @return
	 */
	@RequestMapping(value="/direct",method=RequestMethod.GET)
	public String directchange(@RequestParam("cityandarea") String cityarea,
								HttpSession session,HttpServletResponse response){
		String[] cityareaArray = cityarea.split(" ");
		
		Address address = new Address();
		address.setCity(cityareaArray[0]);
		address.setArea(cityareaArray[1]);
		address.setAddress();
		
		SessionUtil.toSession("userAddress", address, "userAddressC", address, Constants.COOKIE_SAVETIME, response, session);
		return "index";
	}
	/**
	 * 直接搜索
	 * @param session
	 * @param city
	 * @param area
	 * @return
	 */
	@RequestMapping(value="/detail",method=RequestMethod.GET)
	public String detailsinsert(@RequestParam("arrcity") String city,@RequestParam("detail")String area,
								HttpSession session,HttpServletResponse response){
		Address address = new Address();
		address.setCity(city);
		address.setArea(area);
		address.setAddress();
		
		SessionUtil.toSession("userAddress", address, "userAddressC", address, Constants.COOKIE_SAVETIME, response, session);
		return "index";
	}
}
