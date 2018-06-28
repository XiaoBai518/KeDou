package com.kedou.util;

/**
 * @author 张天润
 *
 */
public final class Constants {

	/** 一页默认显示个数 */
    public static final int DEFAULT_PAGESIZE = 4;
    
	/** 课程搜索结果展示页 一页默认显示个数 */
    public static final int DEFAULT_PAGESIZE_COURSE = 3;
    
    /**商家个人中心 课程展示默认个数*/
    public static final int BUSINESSADMIN_DEFAULT_PAGESIZE = 10;
    
    /** 默认的上传地址*/
    public static final String UPLOADURL ="D:\\KedouImg";
    /** 默认的用户头像上传地址*/
    public static final String UPLOADURL_PERSONAL="D:\\KedouImg\\personal";
    /**默认的课程图片上传地址*/
    public static final String UPLOADURL_COURSE="D:\\KedouImg\\course";
    
    /**默认的文章预览图片上传地址*/
    public static final String UPLOADURL_TEMP="D:\\KedouImg\\temp";
    
    /**默认的机构文章图片上传地址*/
    public static final String UPLOADURL_ARTICLE="D:\\KedouImg\\article";
    /**默认的机构公告图片上传地址*/
    public static final String UPLOADURL_NOTICE_BUSINESS="D:\\KedouImg\\business\\notice";
    
    /**订单未处理状态*/
    public static final int TORDER_STATE_UNTREATED = 0;
    /**订单已处理状态*/
    public static final int TORDER_STATE_TREATED = 1;
    
    /**机构账号未激活状态*/
    public static final int BUSINESS_STATE_NOACTIVE = 0;
    /**机构账号已激活状态*/
    public static final int BUSINESS_STATE_ACTIVE = 1;
    /**机构账号被锁定状态*/
    public static final int BUSINESS_STATE_LOCK = 2;
    /**机构角色ID*/
    public static final int BUSINESS_ROLEID = 3;
    
    /**用户账号未激活状态*/
    public static final int USER_STATE_NOACTIVE = 0;
    /**用户账号已激活状态*/
    public static final int USER_STATE_ACTIVE = 1;
    /**用户账号被锁定状态*/
    public static final int USER_STATE_LOCK = 2;
    
    
    /**管理员激活状态*/
    public static final int ADMIN_STATE_ACTIVE = 1;
    /**管理员被锁定状态*/
    public static final int ADMIN_STATE_LOCK = 2;
    
    /**cookie 默认保存时间*/
    public static final int COOKIE_SAVETIME = 7*24*60*60;
    
    /**邮箱注册发送验证信息*/
    public static final String SEND_MAIL1 ="<h1>尊敬的用户您好,请点击下面链接完成激活操作</h1><h3><a href='http://10.7.89.190:8080/KeDou/user/verify?verifyNum=";
    public static final String SEND_MAIL2 = "'>邮箱激活链接</a></h3>";
    /**绑定邮箱发送验证码信息*/    
    public static final String SEND_MAILCODE1 = "<h1>尊敬的用户您好,此次操作的验证码为";
    public static final String SEND_MAILCODE2 = "</h1>";    
    
    /**极简模式下 考研类搜索 第一条展示的问题Id*/
    public static final int MINIMALISTSEARCH_KAOYAN = 1;
    /**极简模式下 四六级类搜索 第一条展示的问题Id*/
    public static final int MINIMALISTSEARCH_SILIUJI = 5;
    
    /**搜索结果展示页面 推荐课程数量*/
    public static final int RECOMMEND_SHOWSIZE = 3;
    
    
    
    
}
