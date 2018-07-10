package com.jk51.shiroTest.web;

import com.jk51.shiroTest.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018/7/5
 * 修改记录:
 */
@Controller
public class LoginController {

    @Autowired
    private SessionDAO sessionDao;

    @RequestMapping(value = "login",method = RequestMethod.GET)
    @ResponseBody
    public String  login(HttpServletRequest request){

        String account = request.getParameter("account");
        String password = request.getParameter("password");

        UsernamePasswordToken upt = new UsernamePasswordToken(account, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            upt.setRememberMe(true);
            subject.login(upt);
            Session session = subject.getSession();
            session.setAttribute("aaa","123");


        } catch (AuthenticationException e) {
            e.printStackTrace();

            return e.getMessage();
        }
        return "success";
    }

    @RequestMapping("test")
    @ResponseBody
    public String test(HttpServletRequest request){


        Subject currentUser =  SecurityUtils.getSubject();

        Session session = currentUser.getSession();

        session.setAttribute("someKey","value");

        if(!currentUser.isAuthenticated()){

            UsernamePasswordToken token = new UsernamePasswordToken("admin","123456");
            token.setRememberMe(true);

            try{
                currentUser.login(token);
            }catch (Exception e){
                System.out.println(e.fillInStackTrace());
                System.exit(1);
            }

        }

        System.out.println("User [" + currentUser.getPrincipal() + "] logged in successfully." );

        if ( currentUser.isPermitted( "lightsaber:weild" ) ) {
            System.out.println("You may use a lightsaber ring.  Use it wisely.");
        } else {
            System.out.println("Sorry, lightsaber rings are for schwartz masters only.");
        }

        if ( currentUser.isPermitted( "winnebago:drive:eagle5" ) ) {
            System.out.println("You are permitted to 'drive' the 'winnebago' with license plate (id) 'eagle5'.  " +
                    "Here are the keys - have fun!");
        } else {
            System.out.println("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }

        return "success";
    }


    @RequestMapping("testNotweb")
    @ResponseBody
    public void testNotweb(HttpServletRequest request){

       String sessionId =  request.getHeader("sessionToken");
       Session session =  sessionDao.readSession(sessionId);
    }

    @RequestMapping("logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "success";
    }

    @RequestMapping("/testForamtter")
    @ResponseBody
    public void formatter(User user1){

        return;
    }

}
