package com.kedou.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class UpLoadUtil {

	/**
	 * 上传图片
	 * @param file
	 * @return    
	 *  写文件时出错时   返回-1
	 *  成功                    返回用户fileName
	 */
	public static String uploadImg (MultipartFile file,String type) {
		
	           //文件后缀
				String tail = "."+file.getOriginalFilename().split("\\.")[1];
				String sdate=(new SimpleDateFormat("yyyyMMddHHmmss")).format( new Date());  
				  //上传文件名
		           String filename = sdate+tail;
	           File filepath = new File(Constants.UPLOADURL_PERSONAL,filename);
	           //判断路径是否存在，如果不存在就创建一个
	           if (!filepath.getParentFile().exists()) { 
	               filepath.getParentFile().mkdirs();
	           }
	           //将上传文件保存到一个目标文件当中
	           try {
	        	   switch(type) {
	        	   case "user": {
	        		   file.transferTo(new File(Constants.UPLOADURL_PERSONAL+ File.separator + filename));
	        		   break;
	        	    }
	        	   case "business" :{
//	        		   file.transferTo(new File(Constants.UPLOADURL_PERSONAL+ File.separator + filename));
//	        		   break;
	        	   }
	        	   case "course" :{
	        		  
	        		   file.transferTo(new File(Constants.UPLOADURL_COURSE+ File.separator + filename));
	        		   break;
	        	   }
	        	   case "admin" :{
//	        		   file.transferTo(new File(Constants.UPLOADURL_PERSONAL+ File.separator + filename));
//	        		   break;
	        	   }
	        	  }
				
			} catch (IllegalStateException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				return "-1";
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				return "-1";
			}
	           return filename;
	}
}
