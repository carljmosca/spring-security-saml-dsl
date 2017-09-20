package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/saml")
public class SSOController {

    // Logger
    private static final Logger LOG = LoggerFactory
            .getLogger(SSOController.class);

    @RequestMapping(value = "/idpSelection", method = RequestMethod.GET)
    public String idpSelection(HttpServletRequest request, Model model) {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            LOG.warn("The current user is already logged.");
            return "redirect:/index.html";
        } else {
            if (isForwarded(request)) {
                //Set<String> idps = metadata.getIDPEntityNames();
                //for (String idp : idps)
                //	LOG.info("Configured Identity Provider for SSO: " + idp);
                //model.addAttribute("idps", idps);
                return "saml/idpselection";
            } else {
                LOG.warn("Direct accesses to '/idpSelection' route are not allowed");
                return "redirect:/";
            }
        }
    }

    /*
     * Checks if an HTTP request has been forwarded by a servlet.
     */
    private boolean isForwarded(HttpServletRequest request){
        if (request.getAttribute("javax.servlet.forward.request_uri") == null)
            return false;
        else
            return true;
    }

}

