package com.learn.sky.web.entity.constant;

public class Constants {

    /*
     *成功返回MSG
     */
    public static final String SUC_MSG = "success";

    /*
     *成功返回Code
     */
    public static final Integer SUC_CODE = 0;

    /*
     *失败返回Code
     */
    public static final Integer ERR_CODE = 1;


    /*
     *失败且返回data的Code
     */
    public static final Integer ERR_WITHDATA_CODE = 2;


    /**
     * 用户登录信息
     */
    public static final String USERKEY = "LoginUserInfo";

    /*
     *用户后台登录信息
     */
    public static final String ADMIN_LOGIN_INFO = "AdminLoginInfo";

    /*
     *是否是超级管理员
     */
    public static final String BE_SUPER = "BeSuper";

    /*
     *用户后台部门信息
     */
    public static final String ADMIN_DEPT_INFO = "AdminDeptInfo";

    /*
     *没登录
     */
    public static final String LOGIN_CODE = "302";

    /*
     *无权限，需要重新登录
     */
    public static final String NO_PMS_LOGIN = "3021";

    /*
     *无权限
     */
    public static final String NO_PMS = "303";

    public static final int DESC_LENGTH = 512;

    public static final int NAME_LENGTH = 62;

    public static final Double HUNDRED = 100.0;

    public static final Long THREE_MONTH = 60 * 60 * 24 * 30 * 3L;

}
