package com.nju.software.SellingCell.data.entity;

import lombok.Data;

public @Data class Goods {
    int goodsid;
    String title;
    String introduction;
    String detail;//
    double price;//价格
    int sold_quantity;//出售数量
    int sellerid;//卖家
    String image;//默认图片

}
