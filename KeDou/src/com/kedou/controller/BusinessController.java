package com.kedou.controller;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kedou.entity.Business;
import com.kedou.service.BusinessService;

@Controller
@RequestMapping("/business")
public class BusinessController {
	@Resource
	private BusinessService businessService;
	
	public BusinessService getBusinessService() {
		return businessService;
	}

	public void setBusinessService(BusinessService businessService) {
		this.businessService = businessService;
	}
	
	//商家注册主页面
	@RequestMapping(value="/registry",method=RequestMethod.POST)
	public String registry(@RequestParam("busAccount") String busAccount,
						@RequestParam("busPwd") String busPwd,
						@RequestParam("randomCode") String randomCode,
						HttpSession session){
		Boolean b = businessService.add(busAccount);
		String code = (String)session.getAttribute("VerificationCode");				//验证码图片上的数字/字母
		if(b&&code.equals(randomCode)){
			session.setAttribute("busAccount", busAccount);
			session.setAttribute("busPwd", busPwd);
			return "busregistry2";		//进入下一步								
		}
		if(!b){
			JOptionPane.showMessageDialog(null, "账号存在!");
			return "busregistry1";
		}
		if(code!=randomCode){
			JOptionPane.showMessageDialog(null, "验证码错误!");
			return "busregistry1";
		}else{
			JOptionPane.showMessageDialog(null, "请重新填写!");
			return "busregistry1";
		}
	}
	
	//添加商家个人信息
	@RequestMapping(value="registry1",method=RequestMethod.POST)
	public String registry1(@RequestParam("busContact") String busContact,
						@RequestParam("busTel") String busTel,
						@RequestParam("busEmail") String busEmail,
						HttpServletResponse response,
						HttpSession session){
		Boolean b = businessService.add1(busEmail);
		if(b){
			session.setAttribute("busContact", busContact);
			session.setAttribute("busTel", busTel);
			session.setAttribute("busEmail", busEmail);
			Properties prop = System.getProperties();
			prop.put("mail.transport.protocol", "smtp");
			prop.put("mail.smtp.host", "smtp.163.com");
			prop.put("mail.smtp.auth", "true");
			
			Session session1 = Session.getInstance(prop, new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("yzq_sunny", "yzq_sunnysqm");
				}
			});
			
			Message msg = new MimeMessage(session1);
			try{
				msg.setFrom(new InternetAddress("yzq_sunny@163.com"));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(busEmail));
				msg.setSubject("邮箱激活");
				MimeBodyPart mimeBodyPart = new MimeBodyPart();
				mimeBodyPart.setContent(busEmail+"您的邮箱已激活", "text/html;charset=UTF-8");
				MimeMultipart mimeMultipart = new MimeMultipart();
				mimeMultipart.addBodyPart(mimeBodyPart);
				msg.setContent(mimeMultipart);
				msg.setHeader("X-Mailer", "smtpsend");
				msg.setSentDate(new Date());
				Transport.send(msg);
				response.getWriter().print("ok");
				
			}catch(Exception e){
				e.printStackTrace();
			}
			return "busregistry3";
		}else{
			JOptionPane.showMessageDialog(null, "邮箱已存在!");
			return "busregistry2";
		}
	}
	
	//添加商家信息
	@RequestMapping(value="/registry2",method=RequestMethod.POST)
	public String registry2(@RequestParam("busName") String busName,
							@RequestParam("busLicense") String busLicense,
							@RequestParam("busAddress") String busAddress,
							@RequestParam("busCorporate") String busCorporate,
							HttpSession session,HttpServletResponse response){
		businessService.add2(busName, busLicense, busAddress, busCorporate, session,response);
		System.out.println("111111111"+busName+","+busLicense+","+busAddress);
		response.setContentType("text/html;charset=utf-8");
		return "buslogin";
	}
	
	//商家登录
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@RequestParam("busAccount") String busAccount,
					@RequestParam("busPwd") String busPwd,
					@RequestParam("randomCode") String randomCode,
					HttpSession session){
		String code = (String)session.getAttribute("VerificationCode");	
		Boolean b = businessService.check(busAccount, busPwd,session);
		if(b&&code.equals(randomCode)){
			JOptionPane.showMessageDialog(null, "登录成功!");
			return "buscenter";
		}	
		if(!(code.equals(randomCode))){
			JOptionPane.showMessageDialog(null, "验证码错误!");
			return "buslogin";
		}else{
			JOptionPane.showMessageDialog(null, "账号或密码错误!");
			return "buslogin";
		}
	}
	//更该商家状态
	@RequestMapping(value="/update",method=RequestMethod.GET)
	public String update(@RequestParam("busId") int busId){
		businessService.update(busId);
		return "forward:findById";
	}
	//根据ID查询商家
	@RequestMapping(value="/findById",method=RequestMethod.GET)
	public String findById(@RequestParam("busId") int busId,
							HttpSession session){
		Business business = businessService.getById(busId);
		session.setAttribute("business", business);
		return "buscenter";
	}
	//查询所有用户
	@RequestMapping(value="/findAll",method=RequestMethod.GET)
	public String findAll(HttpSession session){
		List<Business> list = businessService.findAll();
		session.setAttribute("list", list);
		return "buslist";
	}
	
}
