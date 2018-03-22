package com.nju.software.SellingCell.controller.vo;

public class ResultCode {
    public static int SUCCESS=0;//访问成功
    public static int NOT_AUTHORIZATION=1;//没有认证
    public static int PERMISSION_DENIED=2;//访问无授权
    public static int PUTON_FAILURE=3;//发布失败
    public static int GOODS_NOT_EXIST=4;//商品不存在
    public static int NO_OWNERSHIP=5;//商品不是本商家发布的
    public static int PARAMS_INVALID=6;//参数非法
    public static int SYSTEM_ERROR=7;//系统错误
}
