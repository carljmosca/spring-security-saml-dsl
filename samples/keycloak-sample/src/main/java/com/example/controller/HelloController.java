package com.example.controller;

import com.example.data.HelloResponse;
import org.opensaml.saml2.core.impl.NameIDImpl;
import org.springframework.security.providers.ExpiringUsernameAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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

}
