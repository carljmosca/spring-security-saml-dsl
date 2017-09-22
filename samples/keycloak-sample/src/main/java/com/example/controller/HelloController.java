package com.example.controller;

import com.example.data.HelloResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import org.opensaml.saml2.core.impl.NameIDImpl;
import org.springframework.security.providers.ExpiringUsernameAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Controller
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    HelloResponse sayHello(HttpServletRequest request,
            @RequestParam(value = "name", required = false, defaultValue = "tomcat") String name) {

        HelloResponse response = new HelloResponse();
        String username = "";
        if (request.getUserPrincipal() instanceof ExpiringUsernameAuthenticationToken) {
            ExpiringUsernameAuthenticationToken token = (ExpiringUsernameAuthenticationToken) request.getUserPrincipal();
            NameIDImpl nameId = (NameIDImpl) token.getPrincipal();
            username = nameId.getValue();
        }
        request.getUserPrincipal().getName();
        response.setMessage(String.format("Name: %s User Principal: %s", name, username));
        return response;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public @ResponseBody
    String logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
//        HttpSession session = request.getSession(false);
//        SecurityContextHolder.clearContext();
//        session = request.getSession(false);
//        if (session != null) {
//            session.invalidate();
//        }
//        
        try {
            request.logout();
        } catch (ServletException ex) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
        }
//        try {
//            //request.getSession().invalidate();
//            request.logout();
//            for (Cookie cookie : request.getCookies()) {
//                if ("jsessionid".equalsIgnoreCase(cookie.getName())) {
//                    response.addCookie(cookie);       
//                    response.sendRedirect("/saml/SSO?GLO=true");
//                }
//            }
//        } catch (ServletException ex) {
//            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return "";
    }

}
