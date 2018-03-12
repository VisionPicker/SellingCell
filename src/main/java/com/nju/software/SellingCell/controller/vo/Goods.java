package com.nju.software.SellingCell.controller.vo;

import lombok.Data;

public @Data class Goods {
    String title;
    String introduction;
    String detail;
    double price;
    String img;
    int id;
    boolean putOn=true;//删除只是改变putOn的标志位false

}
