/*
* 文件名：LoginController.java
* 版权：Copyright by www.kitwee.com
* 描述：用户登录控制器
* 修改人：HyDe
* 修改时间：2017/9/14
* 跟踪单号：
* 修改单号：
* 修改内容：
*/

package org.springnext.manager.base.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @RequestMapping("/")
    public String view() {
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
    		    .getAuthentication()
    		    .getPrincipal();
        return "/index";
    }

}
