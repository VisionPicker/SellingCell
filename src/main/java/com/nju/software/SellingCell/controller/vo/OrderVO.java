package com.nju.software.SellingCell.controller.vo;

import lombok.Data;

import java.util.List;

public @Data class OrderVO {
    String orderid;
    long ordered_time;
    double total;
    List<OrderItemVO> list;
}
