package com.nju.software.SellingCell.service;

import com.nju.software.SellingCell.controller.vo.GoodsVO;
import com.nju.software.SellingCell.dto.GoodsDTO;

import java.util.List;

public interface GoodsService {

    public List<GoodsVO> getAllGoodsIntroByVisitorView();

    public List<GoodsDTO> getAllGoodsIntroByCustomerView(int customerId);

    public List<GoodsDTO> getAllGoodsIntroBySellerView();

    public GoodsDTO getGoodsDetailInfo(int goodsId);

    public int putOnGoods(int sellerid,GoodsVO goods);

    public GoodsDTO getGoodsDetailInfoByCustomer(int goodsid,int customerid);

    public boolean putOffGoods(int goodsId,int sellerid);

    public boolean modifyGoodsDetailInfo(GoodsVO goods);

    public List<GoodsDTO> getGoodsIntroByNotPurchasedView(int customerId);

    public List<GoodsDTO> getGoodsIntroByPurchasedView(int customerId);

}
