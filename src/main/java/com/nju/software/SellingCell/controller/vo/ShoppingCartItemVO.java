package com.nju.software.SellingCell.controller.vo;

import lombok.Data;

public @Data class ShoppingCartItemVO {
    int goodsid;
    String title;
    String img;
    double price;
    int quantity;
}
