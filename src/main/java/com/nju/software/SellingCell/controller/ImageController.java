package com.nju.software.SellingCell.controller;

import com.nju.software.SellingCell.controller.vo.Result;
import com.nju.software.SellingCell.controller.vo.ResultCode;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.LogManager;

import static java.lang.System.out;

@RestController
public class ImageController {
    private static Logger logger= org.apache.logging.log4j.LogManager.getLogger(ImageController.class);

    @Value("${images_root}")
    private   String imagesRoot;

    @Value("${name_split}")
    private  String name_split;

    @RequestMapping(value = "/api/user/seller/images/upload",method = RequestMethod.POST)
    public Result uploadImage(HttpServletRequest httpServletRequest){
        MultipartFile multipartFile;
        byte[] imageBytes=null;
        Result result=new Result();
        HashMap<String,String> data=new HashMap<>();
        result.setData(data);
        if(!(httpServletRequest instanceof MultipartHttpServletRequest)){
            result.setSuccess(false);
            result.setCode(ResultCode.PARAMS_INVALID);
            data.put("reason","POST REQUEST NOT MULTIPART/FORM-DATA");
            return result;
        }else{
            MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest)httpServletRequest;
            List<MultipartFile> multipartFiles=multipartRequest.getFiles("file");
            if(multipartFiles!=null && multipartFiles.size()>0){
                multipartFile=multipartFiles.get(0);
                FileOutputStream outputStream=null;
                try {
                    imageBytes=multipartFile.getBytes();
                    String oldName=multipartFile.getOriginalFilename();
                    if(StringUtils.isEmpty(oldName)){
                        result.setSuccess(false);
                        result.setCode(ResultCode.PARAMS_INVALID);
                        return result;
                    }//输入参数有问题，http请求参数不正确，正常操作是不可能出现的
                    String[] temp=oldName.split("\\.");
                    String type=temp[temp.length-1];//获得类型名
                    String imageName="image"+name_split+new Date().getTime()+"."+type;//新的文件名
                    File file=new File(imagesRoot+imageName);
                    outputStream=new FileOutputStream(file);
                    outputStream.write(imageBytes);
                    outputStream.flush();
                    result.setSuccess(true);
                    data.put("image",imageName);
                } catch (IOException e) {
                    result.setSuccess(false);
                    result.setCode(ResultCode.SYSTEM_ERROR);
                    logger.error(e.getStackTrace());

                }finally {
                    if(outputStream!=null){
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            logger.error(e.getStackTrace());
                        }
                    }
                }

            }
        }
        return result;
    }

}
