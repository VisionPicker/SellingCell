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

    @RequestMapping(value="api/user/customer/goods/list/purchased",method = RequestMethod.GET)
    public Result getCustomerPurchasedGoods(HttpServletRequest servletRequest){
        return null;

    }

    @RequestMapping(value="api/user/customer/goods/list/not_purchased",method = RequestMethod.GET)
    public Result getCustomerNotPurchasedGoods(HttpServletRequest servletRequest){
        Result result=new Result();
        int userid=(Integer)servletRequest.getSession().getAttribute(AuthorizationController.token_name);
        List<GoodsDTO> list=goodsService.getGoodsIntroByNotPurchasedView(userid);
        result.setSuccess(true);
        result.setData(list);
        return result;

    }

    @RequestMapping(value = "api/user/seller/goods/puton")
    public Result publishGoods(HttpServletRequest servletRequest,@RequestBody GoodsVO goods){
        Result result=new Result();
        if(!isGoodsParamValid(goods)){
            result.setSuccess(false);
            result.setCode(ResultCode.PARAMS_INVALID);
        }
        int sellerid=(Integer)servletRequest.getSession().getAttribute(AuthorizationController.token_name);
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

        return result;

    }
    //商品下架
    @RequestMapping(value = "/api/user/seller/goods/putoff/{goodsid}",method = RequestMethod.DELETE)
    public Result putoffGoods(HttpServletRequest servletRequest,@PathVariable int goodsid){
        Result result=new Result();
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

    @RequestMapping(value = "/api/user/seller/goods/modify",method=RequestMethod.POST)
    public Result modifyGoods(@RequestBody GoodsVO goodsVO){
        Result result=new Result();
        if(goodsVO.getGoodsid()==-1){
            result.setSuccess(false);
            result.setCode(ResultCode.GOODS_NOT_EXIST);
        }else{
            if(!isGoodsParamValid(goodsVO)){
                result.setSuccess(false);
                result.setCode(ResultCode.PARAMS_INVALID);
            }
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

    private boolean isGoodsParamValid(GoodsVO vo){
        return isValid(vo.getTitle(),2,80)&&
                isValid(vo.getIntroduction(),2,140)&&
                isValid(vo.getDetail(),2,1000)&&
                isValid(vo.getImg(),2,1000);
    }

    private boolean isValid(String s,int min,int max){
        if(s==null){
            return false;
        }
        s=s.trim();
        if(s.length()>max||s.length()<min){
            return false;
        }
        return true;
    }
}
