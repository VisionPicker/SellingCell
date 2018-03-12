package com.nju.software.SellingCell.service;

import com.nju.software.SellingCell.controller.vo.Goods;
import com.nju.software.SellingCell.dto.GoodsDTO;

import java.util.List;

public interface GoodsService {

    public List<GoodsDTO> getAllGoodsIntroByVisitorView();

    public List<GoodsDTO> getAllGoodsIntroByCustomerView(int customerId);

    public List<GoodsDTO> getAllGoodsIntroBySellerView(int sellerId);

    public GoodsDTO getGoodsDetailInfo(int goodsId);

    public boolean putOnGoods(Goods goods);

    public boolean putOffGoods(int goodsId);

    public boolean modifyGoodsDetailInfo(Goods goods);

    public List<GoodsDTO> getGoodsIntroByNotPurchasedView(int customerId);

    public List<GoodsDTO> getGoodsIntroByPurchasedView(int customerId);
}
