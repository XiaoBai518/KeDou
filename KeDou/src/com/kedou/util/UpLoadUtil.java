package com.kedou.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
		           String dirname = null;
		           switch(type) {
	        	   case "user": {
	        		   dirname = Constants.UPLOADURL_PERSONAL;
	        		   break;
	        	    }
	        	   case "business" :{
//	        		   dirname = Constants.UPLOADURL_PERSONAL;
//	        		   break;
	        	   }
	        	   case "course" :{
	        		   dirname = Constants.UPLOADURL_COURSE;
	        		   break;
	        	   }
	        	   case "notice" :{
	        		   dirname = Constants.UPLOADURL_NOTICE_BUSINESS;
	        		   break;
	        	   }
	        	   case "article" :{
	        		   dirname = Constants.UPLOADURL_ARTICLE;
	        		   break;
	        	   }
	        	   case "articleTemp" :{
	        		   dirname = Constants.UPLOADURL_TEMP;
	        		   break;
	        	   }
	        	   case "admin" :{
//	        		    dirname = Constants.UPLOADURL_PERSONAL;
//	        		   break;
	        	   }
	        	  }
	           File filepath = new File(dirname,filename);
	           //判断路径是否存在，如果不存在就创建一个
	           if (!filepath.getParentFile().exists()) {
	        	   System.out.println("不存在目录");
	               filepath.getParentFile().mkdirs();
	           }
	           //将上传文件保存到一个目标文件当中
	           try {
	        	   file.transferTo(new File(dirname+ File.separator + filename));
				
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
	/**
	 * 文件复制
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	public static void copyFile(File source, File dest) {    
	        try {
				Files.copy(source.toPath(), dest.toPath());
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
	}
}
