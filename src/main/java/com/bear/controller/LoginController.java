package com.bear.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    /**
     * 登录成功跳转到主页
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model){
        return "index";
    }

    /**
     * 用户登录的入口
     *
     * @param username
     * @param password
     * @param model
     * @return
     */
    @RequestMapping("/login")
    public String login(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "remember", required = false) String remember,
            Model model,
            HttpSession session) {

        System.out.println("登陆用户输入的用户名：" + username + "，密码：" + password);
        String error = null;
        if (username != null && password != null) {
            //初始化
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            if (remember != null){
                if (remember.equals("on")) {
                    //说明选择了记住我
                    token.setRememberMe(true);
                } else {
                    token.setRememberMe(false);
                }
            }else{
                token.setRememberMe(false);
            }

            try {
                //登录，即身份校验，由通过Spring注入的UserRealm会自动校验输入的用户名和密码在数据库中是否有对应的值
                subject.login(token);
                System.out.println("用户是否登录：" + subject.isAuthenticated());
                // 重定向次级目录 /login --> / --> index
                return "redirect:index.do";
            } catch (UnknownAccountException e) {
                e.printStackTrace();
                // error = "用户账户不存在，错误信息：" + e.getMessage();
                error = "用户账户不存在";
            } catch (IncorrectCredentialsException e) {
                e.printStackTrace();
                // error = "用户名或密码错误，错误信息：" + e.getMessage();
                error = "用户名或密码错误";
            } catch (LockedAccountException e) {
                e.printStackTrace();
                error = "该账号已锁定，错误信息";
            } catch (DisabledAccountException e) {
                e.printStackTrace();
                error = "该账号已禁用，错误信息";
            } catch (ExcessiveAttemptsException e) {
                e.printStackTrace();
                error = "该账号登录失败次数过多，错误信息";
            } catch (Exception e){
                e.printStackTrace();
                error = "未知错误，错误信息";
            }
        } else {
            error = "请输入用户名和密码";
        }
        // 登录失败，跳转到login页面
        model.addAttribute("error", error);
        // 请求转发到WEB-INF下的login.jsp
        return "login";
    }


    /**
     * 用户注册页面
     * @return
     */
    @RequestMapping("/register")
    public String register(){
        return "register";
    }
}
