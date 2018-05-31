package com.kedou.util;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;


import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 *  邮件发送工具
 *  使用方法 ： new SendEmailThread(emailAddress,code).start();
 * @author 原源
 *
 */
public class SendEmailThread extends Thread {

	private String emailAddress;//邮箱地址
	private String code;        //标识码
	
	//构造方法
	public SendEmailThread(String email,String code) {
		emailAddress = email;
		this.code = code;
	}
	
@Override
public void run() {
	System.out.println("线程开始");
    //获取到用户所注册邮箱的类别
	String[] hostName = emailAddress.split("@");
	String hostName1 = "smtp."+hostName[1];
	
	Properties props = new Properties();
	// 设置发送邮件的邮件服务器的属性
	props.put("mail.smtp.host", hostName1);
	props.put("mail.smtp.auth", "true");
	// 开启SSL加密，否则会失败
			MailSSLSocketFactory sf=null;
			try {
				sf = new MailSSLSocketFactory();
			} catch (GeneralSecurityException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			sf.setTrustAllHosts(true);
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.ssl.socketFactory", sf);

	// 用session为参数定义消息对象
	Session session = Session.getInstance(props, new Authenticator() {
        public PasswordAuthentication getPasswordAuthentication() { /* 若服务器需要身份认证，Sission会自动调用这个方法 */
            return new PasswordAuthentication("949947078@qq.com", "gekcahyzfvqdbbhe");
        }
    });
	// 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
	// 用（你可以在控制台（console)上看到发送邮件的过程）
	session.setDebug(true);
	MimeMessage message = new MimeMessage(session);
    try {  
    	// 加载发件人地址
    	message.setFrom(new InternetAddress("949947078@qq.com"));
    	// 加载收件人地址
    	message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
    	// 加载标题
    	message.setSubject("课兜网邮箱激活");
    	// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
    	Multipart multipart = new MimeMultipart();
    	// 设置邮件的文本内容
    	BodyPart contentPart = new MimeBodyPart();
    	//加上"text/html; charset=utf-8"，表示支持html格式的邮件
    	contentPart.setContent("<h1>尊敬的用户您好,请点击下面链接完成激活操作</h1><h3><a href='http://localhost:8080/KeDou/user/verify?verifyNum="+code+"'>邮箱激活链接</a></h3>", "text/html; charset=utf-8");
    	multipart.addBodyPart(contentPart);
    	message.setContent(multipart);
    	message.setHeader("X-Mailer", "smtpsend");
    	message.setSentDate(new Date());
    	// 保存邮件
    	message.saveChanges();
    	// 发送邮件
    	Transport.send(message);
    	System.out.println("线程结束");
    } catch (Exception e) {
    	e.printStackTrace();
    }
}
}
