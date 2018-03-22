package com.nju.software.SellingCell.controller;

import com.nju.software.SellingCell.controller.vo.GoodsVO;
import com.nju.software.SellingCell.controller.vo.Result;
import com.nju.software.SellingCell.controller.vo.ResultCode;
import com.nju.software.SellingCell.data.entity.Goods;
import com.nju.software.SellingCell.dto.GoodsDTO;
import com.nju.software.SellingCell.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController
public class GoodsController {
    @Autowired
    GoodsService goodsService;

    @RequestMapping(value="api/goods/info/{goodsId}",method= RequestMethod.GET)
    public Object findGoodInfo(HttpServletRequest servletRequest,@PathVariable int goodsId){
        Result result=new Result();
        GoodsDTO goods=null;
        //作为游客访问的时候
        if(servletRequest.getSession().getAttribute(AuthorizationController.token_name)==null){
            goods=goodsService.getGoodsDetailInfo(goodsId);
        }else{
            int userid=(Integer)servletRequest.getSession().getAttribute(AuthorizationController.token_name);
            String role=(String)servletRequest.getSession().getAttribute(AuthorizationController.user_role);
        //作为卖家访问的时候
            if(role.equals("seller")){
                goods=goodsService.getGoodsDetailInfo(goodsId);
            }else{
                goods=goodsService.getGoodsDetailInfoByCustomer(goodsId,userid);//通过顾客视角查询

            }
        }
        if(goods==null||goods.getGoodsid()==-1){
            result.setSuccess(false);
            result.setCode(ResultCode.GOODS_NOT_EXIST);
        }else{
            result.setSuccess(true);
            result.setData(goods);
        }
        return result;
    }

    @RequestMapping(value="api/goods/info/all",method = RequestMethod.GET)
    public Result getAllGoods(HttpServletRequest servletRequest){
        Result result=new Result();
        HttpSession session=servletRequest.getSession();
        if(session.getAttribute(AuthorizationController.token_name)==null){
            result.setSuccess(true);
            result.setCode(ResultCode.NOT_AUTHORIZATION);
            List<GoodsVO> list=goodsService.getAllGoodsIntroByVisitorView();
            result.setData(list);
            return result;
        }
        int userid=(Integer)session.getAttribute(AuthorizationController.token_name);
        String role=(String)session.getAttribute(AuthorizationController.user_role);
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        if(role.equals("customer")) {
            List<GoodsDTO> list = goodsService.getAllGoodsIntroByCustomerView(userid);
            result.setData(list);
            return result;
        }else{
            List<GoodsDTO> list = goodsService.getAllGoodsIntroBySellerView();
            result.setData(list);
            return result;
        }


    }

    @RequestMapping(value="api/user/goods/info/customer/purchased",method = RequestMethod.GET)
    public Result getCustomerPurchasedGoods(HttpServletRequest servletRequest){
        return null;

    }

    @RequestMapping(value="api/user/goods/info/customer/not_purchased",method = RequestMethod.GET)
    public Result getCustomerNotPurchasedGoods(HttpServletRequest servletRequest){
        Result result=new Result();
        int userid=(Integer)servletRequest.getSession().getAttribute(AuthorizationController.token_name);
        String role=(String)servletRequest.getSession().getAttribute(AuthorizationController.user_role);
        if(role.equals("seller")){
            result.setSuccess(false);
            result.setCode(ResultCode.PERMISSION_DENIED);
            return result;
        }
        List<GoodsDTO> list=goodsService.getGoodsIntroByNotPurchasedView(userid);
        result.setSuccess(true);
        result.setData(list);
        return result;

    }

    @RequestMapping(value = "api/user/goods/puton")
    public Result publishGoods(HttpServletRequest servletRequest,@RequestBody GoodsVO goods){
        Result result=new Result();
        int sellerid=(Integer)servletRequest.getSession().getAttribute(AuthorizationController.token_name);
        String role=(String)servletRequest.getSession().getAttribute(AuthorizationController.user_role);
        if(role.equals("seller")){
            int goodsid=goodsService.putOnGoods(sellerid,goods);
            if(goodsid!=-1){
                HashMap<String,Integer> hashMap=new HashMap();
                result.setSuccess(true);
                result.setCode(ResultCode.SUCCESS);//发布成功
                hashMap.put("goodsid",goodsid);
                result.setData(hashMap);
            }else {
                result.setSuccess(false);
                result.setCode(ResultCode.PUTON_FAILURE);//发布失败
            }
        }else{
            result.setSuccess(false);
            result.setCode(ResultCode.PERMISSION_DENIED);
        }
        return result;

    }
    //商品下架
    @RequestMapping(value = "/api/user/goods/putoff/{goodsid}",method = RequestMethod.DELETE)
    public Result putoffGoods(HttpServletRequest servletRequest,@PathVariable int goodsid){
        Result result=new Result();
        String role=(String)servletRequest.getSession().getAttribute(AuthorizationController.user_role);
        if(role=="customer") {
            result.setSuccess(false);
            result.setCode(ResultCode.PERMISSION_DENIED);
            return result;
        }
        GoodsDTO dto=goodsService.getGoodsDetailInfo(goodsid);
        if(dto==null||dto.getGoodsid()==-1){
            result.setSuccess(false);
            result.setCode(ResultCode.GOODS_NOT_EXIST);
            return result;
        }
        int sellerid = (Integer) servletRequest.getSession().getAttribute(AuthorizationController.token_name);
        boolean success = goodsService.putOffGoods(goodsid, sellerid);
        result.setSuccess(success);
        if (!success) {
            result.setCode(ResultCode.NO_OWNERSHIP);//说明可能是商品不存在或者这个商品不是商家发布的
        }
        return result;

    }

    @RequestMapping(value = "/api/user/goods/modify",method=RequestMethod.POST)
    public Result modifyGoods(@RequestBody GoodsVO goodsVO){
        Result result=new Result();
        if(goodsVO.getGoodsid()==-1){
            result.setSuccess(false);
            result.setCode(ResultCode.GOODS_NOT_EXIST);
        }else{
            boolean modify_result=goodsService.modifyGoodsDetailInfo(goodsVO);
            if(modify_result){
                result.setSuccess(true);
                HashMap<String,Integer> map=new HashMap<>();
                map.put("goodsid",goodsVO.getGoodsid());
                result.setData(map);
            }else{
                result.setSuccess(false);
                result.setCode(ResultCode.GOODS_NOT_EXIST);
            }
        }
        return result;
    }


}
