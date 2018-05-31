package com.kedou.controller.bg;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.test.annotation.Repeat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.kedou.entity.Business;
import com.kedou.entity.User;
import com.kedou.service.bg.StoreServiceImpl;


@Controller
@RequestMapping("/bg_store")
public class bg_StoreController {
	@Resource
	private StoreServiceImpl storeServiceImpl;
	/**
	 * 锁定选中的商家
	 * @param str
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/bgLockBus",method=RequestMethod.POST)
	@ResponseBody
	public String lockStore(@RequestParam("str")String str,HttpServletRequest request) {
		
		if(str!=null) {
		Gson g = new Gson();
		
		 List<Business> ul = g.fromJson(str, new TypeToken<List<Business>>() {}.getType());//对于不是类的情况，用这个参数给出)
		 int temp=0;
		 int[] array=new int[ul.size()];
		for(Business u:ul) {
			array[temp]=u.getBusId();
			temp++;
		}
		storeServiceImpl.lockBus(array);
		}else {
			
		}
		
		return null;
		
	}
	
	/**
	 * 更新商家数据
	 * @param busId
	 * @param busName
	 * @param busContact
	 * @param busTel
	 * @param busLicense
	 * @param busAddress
	 * @param busCorporate
	 * @param state
	 * @param busAccount
	 * @param busEmail
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/bgEditStore",method=RequestMethod.POST)
	public String bgEditStore(@RequestParam("busId")int busId,
								@RequestParam("busName")String busName,
								@RequestParam("busContact")String busContact,
								@RequestParam("busTel")String busTel,
								@RequestParam("busLicense")String busLicense,
								@RequestParam("busAddress")String busAddress,
								@RequestParam("busCorporate")String busCorporate,
								@RequestParam("state")int state,
								@RequestParam("busAccount")String busAccount,
								@RequestParam("busEmail")String busEmail,
								Model model,HttpSession session,HttpServletRequest request) {
		Business bus=storeServiceImpl.findBusById(busId);
		bus.setBusId(busId);
		bus.setBusName(busName);
		bus.setBusContact(busContact);
		bus.setBusTel(busTel);
		bus.setBusLicense(busLicense);
		bus.setBusAddress(busAddress);
		bus.setBusCorporate(busCorporate);
		bus.setState(state);
		bus.setBusAccount(busAccount);
		bus.setBusEmail(busEmail);
		storeServiceImpl.saveBus(bus);
		return "bg_index";
	}
	
	
	/**
	 * ajax动态查询商家数据
	 * @param account
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/bgSearchBus",method=RequestMethod.GET,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String bgsearchUser(@RequestParam("account")String account,Model model,HttpSession session,HttpServletRequest request) {
		Business bus=storeServiceImpl.findBus(account);
		if(bus!=null) {
			Gson g = new Gson();
			
			return g.toJson(bus);
		}else {
			return "";
		}
		
		
	}
}
