package com.nju.software.SellingCell.service.imp;

import com.nju.software.SellingCell.Constance;
import com.nju.software.SellingCell.controller.vo.GoodsVO;
import com.nju.software.SellingCell.data.GoodsDao;
import com.nju.software.SellingCell.data.OrderDao;
import com.nju.software.SellingCell.data.entity.Goods;
import com.nju.software.SellingCell.dto.GoodsDTO;
import com.nju.software.SellingCell.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;

@Service
public class GoodsServiceImp implements GoodsService{
    @Autowired
    GoodsDao goodsDao;
    @Autowired
    OrderDao orderDao;

    static int CUSTOMER_ALL=0;
    static int CUSTOMER_NOT_PURCHASED=1;
    static int CUSTOMER_PURCHASED=2;

    @Override
    public List<GoodsVO> getAllGoodsIntroByVisitorView() {
        List<GoodsVO> list=goodsDao.selectAllIntroGoodsIntro();
        if(list==null){
            list=new LinkedList<>();
        }
        return list;
    }

    @Override
    public List<GoodsDTO> getAllGoodsIntroByCustomerView(int customerId) {
        return goodsDao.selectAllGoodsIntroByCustomerView(customerId,CUSTOMER_ALL);

    }

    @Override
    public List<GoodsDTO> getAllGoodsIntroBySellerView() {
        List<GoodsDTO> list=goodsDao.selectAllGoodsIntroBySellerView();
        if(list==null){
            list=new LinkedList<>();
        }
        return list;
    }

    @Override
    public GoodsDTO getGoodsDetailInfo(int goodsid) {
        GoodsDTO goodsDTO=new GoodsDTO();
        Goods goods=goodsDao.selectGoods(goodsid);
        if(goods!=null){
            goodsDTO.setGoodsid(goods.getGoodsid());
            goodsDTO.setTitle(goods.getTitle());
            goodsDTO.setIntroduction(goods.getIntroduction());
            goodsDTO.setDetail(goods.getDetail());
            goodsDTO.setPrice(goods.getPrice());
            goodsDTO.setImg(goods.getImage());
            goodsDTO.setSold_quantity(goods.getSold_quantity());
            goodsDTO.setPurchased(false);//seller视角下没有选购属性
        }
        return goodsDTO;
    }

    @Override
    public int putOnGoods(int sellerid,GoodsVO goodsVO) {
        Goods goods=new Goods();
        goods.setTitle(goodsVO.getTitle());
        goods.setIntroduction(goodsVO.getIntroduction());
        if(StringUtils.isEmpty(goodsVO.getImg())){
            goods.setImage("default.jpg");
        }else{
            goods.setImage(goodsVO.getImg());
        }
        goods.setDetail(goodsVO.getDetail());
        goods.setPrice(goodsVO.getPrice());
        goods.setSellerid(sellerid);
        int result=goodsDao.insertGoods(goods);
        if(result==0){
            return -1;
        }else{
            return goods.getGoodsid();//返回生成的商品id
        }
    }

    @Override
    public GoodsDTO getGoodsDetailInfoByCustomer(int goodsid, int customerid) {
        GoodsDTO goodsDTO=new GoodsDTO();
        Goods goods=goodsDao.selectGoods(goodsid);
        if(goods!=null){
            goodsDTO.setGoodsid(goods.getGoodsid());
            goodsDTO.setTitle(goods.getTitle());
            goodsDTO.setIntroduction(goods.getIntroduction());
            goodsDTO.setDetail(goods.getDetail());
            goodsDTO.setPrice(goods.getPrice());
            goodsDTO.setImg(goods.getImage());
            goodsDTO.setSold_quantity(goods.getSold_quantity());
            goodsDTO.setPurchased(false);//默认没有选购
        }
        int purchased_quantity=orderDao.selectOrderItem(goodsid,customerid);
        if(purchased_quantity!=0){
           goodsDTO.setPurchased(true);
        }
        return goodsDTO;
    }

    @Override
    public boolean putOffGoods(int goodsId,int sellerid) {
        int result=goodsDao.updateStatus(goodsId,sellerid, Constance.STATUS_OFF);
        if(result==1){
            return true;
        }
        return false;
    }

    @Override
    public boolean modifyGoodsDetailInfo(GoodsVO goods) {
        int result=goodsDao.updateGoods(goods);
        if(result==1){
            return true;
        }
        return false;
    }

    @Override
    public List<GoodsDTO> getGoodsIntroByNotPurchasedView(int customerId) {

        return goodsDao.selectAllGoodsIntroByCustomerView(customerId,CUSTOMER_NOT_PURCHASED);
    }

    /**
     * 未要求
     * @param customerId
     * @return
     */
    @Override
    public List<GoodsDTO> getGoodsIntroByPurchasedView(int customerId){
        return null;
    }

}
