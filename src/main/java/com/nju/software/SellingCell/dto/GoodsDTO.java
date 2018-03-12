package com.nju.software.SellingCell.dto;

import lombok.Data;

public @Data class GoodsDTO {
    int goodsId;
    String title;
    String introduction;
    String detail;
    String img;
    double price;
    int soldNumber;//对于卖家来说，是出售的数量
    boolean purchased;//对于买家，是已经购买过的标志位
}
