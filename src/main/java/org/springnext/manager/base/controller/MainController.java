package org.springnext.manager.base.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 主控制器
 * @author hyde
 *
 */
@Controller
public class MainController {
	
	@RequestMapping("/login")
    public String login() {
        return "/login";
    }
    
    @RequestMapping("/")
    public String index() {
        return "/index";
    }
    
    @RequestMapping("/main")
    public String main() {
        return "/main";
    }

}
