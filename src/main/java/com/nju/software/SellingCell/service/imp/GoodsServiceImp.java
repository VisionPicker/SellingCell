package com.nju.software.SellingCell.service.imp;

import com.nju.software.SellingCell.controller.vo.Goods;
import com.nju.software.SellingCell.dto.GoodsDTO;
import com.nju.software.SellingCell.service.GoodsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImp implements GoodsService{
    @Override
    public List<GoodsDTO> getAllGoodsIntroByVisitorView() {
        return null;
    }

    @Override
    public List<GoodsDTO> getAllGoodsIntroByCustomerView(int customerId) {
        return null;
    }

    @Override
    public List<GoodsDTO> getAllGoodsIntroBySellerView(int sellerId) {
        return null;
    }

    @Override
    public GoodsDTO getGoodsDetailInfo(int goodsId) {
        return null;
    }

    @Override
    public boolean putOnGoods(Goods goods) {
        return false;
    }

    @Override
    public boolean putOffGoods(int goodsId) {
        return false;
    }

    @Override
    public boolean modifyGoodsDetailInfo(Goods goods) {
        return false;
    }

    @Override
    public List<GoodsDTO> getGoodsIntroByNotPurchasedView(int customerId) {
        return null;
    }

    @Override
    public List<GoodsDTO> getGoodsIntroByPurchasedView(int customerId) {
        return null;
    }
}
