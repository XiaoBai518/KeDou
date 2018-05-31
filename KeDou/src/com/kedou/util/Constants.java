package com.kedou.util;

/**
 * @author 张天润
 *
 */
public final class Constants {

	/** 一页默认显示个数 */
    public static final int DEFAULT_PAGESIZE = 4;
    
    
    /** 默认的上传地址*/
    public static final String UPLOADURL ="D:\\KedouImg";
    /** 默认的用户头像上传地址*/
    public static final String UPLOADURL_PERSONAL="D:\\KedouImg\\personal";
    
    
    /**机构账号未激活状态*/
    public static final int BUSINESS_STATE_NOACTIVE = 0;
    /**机构账号已激活状态*/
    public static final int BUSINESS_STATE_ACTIVE = 1;
    /**机构账号被锁定状态*/
    public static final int BUSINESS_STATE_LOCK = 2;
    /**默认的课程图片上传地址*/
    public static final String UPLOADURL_COURSE="D:\\KedouImg\\course";
    
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
    
    
}
