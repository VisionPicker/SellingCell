package com.nju.software.SellingCell.data;

import com.nju.software.SellingCell.controller.vo.Goods;
import com.nju.software.SellingCell.dto.GoodsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsDao {
    public List<GoodsDTO> selectAllGoodsByCustomerView();

    public List<GoodsDTO> selectAllGoodsIntroByCustomerView(int customerId);

    public List<GoodsDTO> selectAllGoodsIntroBySellerView(int sellerId);

    public GoodsDTO selectGoods(int goodsId);

    public boolean updateGoods(Goods goods);

    public boolean insertGoods(Goods goods);

    public List<GoodsDTO> selectGoodsIntroByNotPurchasedView(int customerId);

    public List<GoodsDTO> selectGoodsIntroByPurchasedView(int customerId);

}
