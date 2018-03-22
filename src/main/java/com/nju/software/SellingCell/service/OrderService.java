package com.nju.software.SellingCell.service;

import com.nju.software.SellingCell.controller.vo.OrderVO;
import com.nju.software.SellingCell.controller.vo.ShoppingCartItemVO;
import com.nju.software.SellingCell.data.entity.ShoppingCartItem;

import java.util.List;

public interface OrderService {

    public boolean addGoodsToShoppingcart(ShoppingCartItem cartItem);

    public List<ShoppingCartItemVO> showShoppingCart(int customerid);

    public boolean modifyShoppingCartQuantity(ShoppingCartItem cartItem);

    public boolean generateOrder(int customerid);

    public List<OrderVO> showCustomerOrder(int customerid);

    public boolean removeCartItem(int goodsid,int customerid);
}
