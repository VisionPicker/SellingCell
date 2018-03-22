package com.nju.software.SellingCell;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ImagesResourceConfig extends WebMvcConfigurerAdapter {
    @Value("${images_root}")
    private String images_root;

    /**
     * 配置静态访问资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/show/**").addResourceLocations("file:"+images_root);
        super.addResourceHandlers(registry);
    }
}