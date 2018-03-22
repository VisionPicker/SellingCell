package com.nju.software.SellingCell.service.imp;

import com.nju.software.SellingCell.controller.vo.OrderItemVO;
import com.nju.software.SellingCell.controller.vo.OrderVO;
import com.nju.software.SellingCell.controller.vo.ShoppingCartItemVO;
import com.nju.software.SellingCell.data.GoodsDao;
import com.nju.software.SellingCell.data.OrderDao;
import com.nju.software.SellingCell.data.entity.Goods;
import com.nju.software.SellingCell.data.entity.OrderItem;
import com.nju.software.SellingCell.data.entity.ShoppingCartItem;
import com.nju.software.SellingCell.dto.GoodsDTO;
import com.nju.software.SellingCell.service.OrderService;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImp implements OrderService{
    @Autowired
    OrderDao orderDao;
    @Autowired
    GoodsDao goodsDao;

    @Override
    public boolean addGoodsToShoppingcart(ShoppingCartItem cartItem) {
        ShoppingCartItem existItem=orderDao.selectCartItem(cartItem.getGoodsid(),cartItem.getCustomerid());
        if(existItem!=null&&existItem.getQuantity()>0){
            cartItem.setQuantity(existItem.getQuantity()+cartItem.getQuantity());
            return orderDao.updateCartItem(cartItem)>0?true:false;
        }else{
            return orderDao.addCartItem(cartItem)>0?true:false;
        }

    }

    @Override
    public List<ShoppingCartItemVO> showShoppingCart(int customerid) {
        List<ShoppingCartItemVO> list=orderDao.selectAllCartItem(customerid);
        return list;
    }

    @Override
    public boolean modifyShoppingCartQuantity(ShoppingCartItem cartItem){
        return orderDao.updateCartItem(cartItem)>0?true:false;
    }

    /**
     * 读取购物车生成order，再修改出售数量，并且删除购物车内容
     * @param customerid
     * @return
     */
    @Override
    @Transactional
    public boolean generateOrder(int customerid) {
        String orderid= UUID.randomUUID().toString();
        long orderd_time= new Date().getTime();
        int result= orderDao.insertOrder(orderid,customerid,orderd_time);
        if(result>0){
            goodsDao.incrementSoldQuantity(customerid);//增加售出数量
            orderDao.deleteCartItem(customerid,null);
        }
        return result>0?true:false;
    }

    @Override
    public List<OrderVO> showCustomerOrder(int customerid) {
        List<OrderItem> list=orderDao.selectOrders(customerid);
        List<OrderVO> orders=new LinkedList<>();
        String now_orderid=null;//获取第一个orderitem的id
        List<OrderItemVO> itemVOList=null;
        OrderVO orderVO=null;
        double total_balance=0;
        for(OrderItem item:list){
            OrderItemVO itemVO=new OrderItemVO();
            itemVO.setGoodsid(item.getGoodsid());
            itemVO.setImg(item.getImg());
            itemVO.setPrice(item.getPrice());
            itemVO.setQuantity(item.getQuantity());
            itemVO.setTitle(item.getTitle());
            itemVO.setTotal(item.getItem_total());
            if(!item.getOrderid().equals(now_orderid)){
                now_orderid=item.getOrderid();//新的order开始
                total_balance=0;
                orderVO=new OrderVO();
                orderVO.setOrdered_time(item.getOrdered_time());
                orderVO.setOrderid(item.getOrderid());
                itemVOList=new LinkedList<>();
                orderVO.setList(itemVOList);
                orders.add(orderVO);
            }
            total_balance+=item.getItem_total();//加上一个item的总价
            orderVO.setTotal(total_balance);//加上item的总价
            itemVOList.add(itemVO);//将所属的itemVO加入到其中
        }
        return orders;
    }

    @Override
    public boolean removeCartItem(int goodsid, int customerid) {
        return orderDao.deleteCartItem(customerid,goodsid)>0?true:false;
    }


}
