package com.nju.software.SellingCell.data.entity;

import lombok.Data;

public @Data class OrderItem {
    int customerid;
    String orderid;
    long ordered_time;
    double item_total;
    int sellerid;
    int itemid;
    String title;
    double price;
    int quantity;
    String img;
    int goodsid;


}
