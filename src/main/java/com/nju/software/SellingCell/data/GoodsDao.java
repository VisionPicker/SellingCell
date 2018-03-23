package com.nju.software.SellingCell.data;

import com.nju.software.SellingCell.controller.vo.GoodsVO;
import com.nju.software.SellingCell.data.entity.Goods;
import com.nju.software.SellingCell.dto.GoodsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsDao {
    public List<GoodsVO> selectAllIntroGoodsIntro();

    public List<GoodsDTO> selectAllGoodsIntroByCustomerView(@Param("customerid")int customerId,@Param("viewtype") int viewType);

    public List<GoodsDTO> selectAllGoodsIntroBySellerView();

    public Goods selectGoods(@Param("goodsid") int goodsId);

    public int updateGoods(GoodsVO goods);

    public int insertGoods(Goods goods);

    public List<GoodsDTO> selectGoodsIntroByNotPurchasedView(int customerId);

    public List<GoodsDTO> selectGoodsIntroByPurchasedView(int customerId);

    public int updateStatus(@Param("goodsid")int goodsid,@Param("sellerid")int sellerid,@Param("status") int status);

    public int incrementSoldQuantity(@Param("customerid") int customerid);

}
