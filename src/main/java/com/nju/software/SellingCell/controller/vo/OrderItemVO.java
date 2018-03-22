package com.nju.software.SellingCell.controller.vo;

import lombok.Data;

public @Data class OrderItemVO {
    int goodsid;
    String img;
    String title;
    int quantity;
    double price;
    double total;
}
