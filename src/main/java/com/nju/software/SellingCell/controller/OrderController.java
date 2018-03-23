package com.nju.software.SellingCell.controller;

import com.nju.software.SellingCell.controller.vo.OrderVO;
import com.nju.software.SellingCell.controller.vo.Result;
import com.nju.software.SellingCell.controller.vo.ResultCode;
import com.nju.software.SellingCell.controller.vo.ShoppingCartItemVO;
import com.nju.software.SellingCell.data.entity.ShoppingCartItem;
import com.nju.software.SellingCell.service.OrderService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class OrderController{
    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/api/user/customer/shoppingcart/add",method = RequestMethod.POST)
    public Result addGoodsToCart(HttpServletRequest servletRequest, @RequestBody ShoppingCartItem cartItem){
        Result result=new Result();
        if(cartItem.getGoodsid()<0||cartItem.getQuantity()<0){
            result.setSuccess(false);
            result.setCode(ResultCode.PARAMS_INVALID);
            return result;
        }
        int userid=(Integer) servletRequest.getSession().getAttribute(AuthorizationController.token_name);
        cartItem.setCustomerid(userid);
        boolean addResult=orderService.addGoodsToShoppingcart(cartItem);
        result.setSuccess(addResult);
        if(!addResult){
            result.setCode(ResultCode.SYSTEM_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/api/user/customer/shoppingcart/show",method = RequestMethod.POST)
    public Result showAllCartItems(HttpServletRequest servletRequest){

        Result result=new Result();
        int userid=(Integer) servletRequest.getSession().getAttribute(AuthorizationController.token_name);
        List<ShoppingCartItemVO> list=orderService.showShoppingCart(userid);
        result.setSuccess(true);
        result.setData(list);
        return result;
    }

    @RequestMapping(value="/api/user/customer/shoppingcart/balance",method=RequestMethod.POST)
    public Result balance(HttpServletRequest servletRequest){
        Result result=new Result();
        int userid=(Integer) servletRequest.getSession().getAttribute(AuthorizationController.token_name);
        boolean balance_result=orderService.generateOrder(userid);
        result.setSuccess(balance_result);
        if(!balance_result){
            result.setCode(ResultCode.SYSTEM_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/api/user/customer/shoppingcart/remove/{goodsid}",method = RequestMethod.DELETE)
    public Result deleteGoodsToCart(HttpServletRequest servletRequest, @PathVariable int goodsid){
        Result result=new Result();
        int userid=(Integer)servletRequest.getSession().getAttribute(AuthorizationController.token_name);
        boolean success=orderService.removeCartItem(goodsid,userid);
        result.setSuccess(success);
        if(!success){
            result.setCode(ResultCode.SYSTEM_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "api/user/customer/orders",method = RequestMethod.GET)
    public Result showOrders(HttpServletRequest servletRequest){
        Result result=new Result();
        int userid=(Integer)servletRequest.getSession().getAttribute(AuthorizationController.token_name);
        List<OrderVO> orders=orderService.showCustomerOrder(userid);
        result.setSuccess(true);
        result.setData(orders);
        return result;
    }

    @RequestMapping(value = "/api/user/customer/shoppingcart/modify",method = RequestMethod.POST)
    public Result updateCartItems(HttpServletRequest servletRequest,@RequestBody ShoppingCartItem cartItem){
        Result result=new Result();
        if(cartItem.getGoodsid()<0||cartItem.getQuantity()<0) {
            result.setSuccess(false);
            result.setCode(ResultCode.PARAMS_INVALID);
            return result;
        }
        int userid=(Integer)servletRequest.getSession().getAttribute(AuthorizationController.token_name);
        cartItem.setCustomerid(userid);
        boolean update_result=orderService.modifyShoppingCartQuantity(cartItem);
        result.setSuccess(update_result);
        if(!update_result){
            result.setCode(ResultCode.SYSTEM_ERROR);
        }
        return result;
    }

}
