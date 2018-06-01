package com.cincc.o2o.web.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author li
 * Date: 2018/05/31
 */
@Controller
@RequestMapping("/local")
public class LocalController {
    /**
     * 注册账号页路由
     */
    @RequestMapping(value = "/register",method = RequestMethod.GET)
    private String register(){
        return "local/register";
    }

    /**
     * 修改密码页路由
     * @return
     */
    @RequestMapping(value = "/changepsw",method = RequestMethod.GET)
    private String changepsw(){
        return "local/changepsw";
    }

    /**
     * 登录页路由
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    private String login(){
        return "local/login";
    }
}
