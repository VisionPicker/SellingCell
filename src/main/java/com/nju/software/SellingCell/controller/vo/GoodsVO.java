package com.nju.software.SellingCell.controller.vo;

import lombok.Data;

public @Data class GoodsVO {
    int goodsid=-1;
    String title;
    String introduction;
    String detail;
    String img;
    String sellerid;
    double price;
}
