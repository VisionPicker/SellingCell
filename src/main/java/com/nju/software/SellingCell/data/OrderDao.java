package com.nju.software.SellingCell.data;

import com.nju.software.SellingCell.controller.vo.ShoppingCartItemVO;
import com.nju.software.SellingCell.data.entity.OrderItem;
import com.nju.software.SellingCell.data.entity.ShoppingCartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderDao {

    public int selectOrderItem(@Param("goodsid")int goodsid,@Param("customerid") int customerid);

    public int addCartItem(ShoppingCartItem cartItem);

    public ShoppingCartItem selectCartItem(@Param("goodsid") int goodsid,@Param("customerid") int customerid);

    public int updateCartItem(ShoppingCartItem cartItem);

    public List<ShoppingCartItemVO> selectAllCartItem(@Param("customerid") int customerid);

    public int insertOrder(@Param("orderid")String orderid,@Param("customerid") int customerid,@Param("ordered_time")long time);

    public int deleteCartItem(@Param("customerid") int customerid,@Param("goodsid")Integer goodsid);//为了能够传入null值

    public List<OrderItem> selectOrders(@Param("customerid") int customerid);
}
