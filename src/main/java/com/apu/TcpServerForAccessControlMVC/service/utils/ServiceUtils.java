package com.apu.TcpServerForAccessControlMVC.service.utils;

import java.security.Principal;

import org.springframework.web.servlet.ModelAndView;

public class ServiceUtils {

    public void setResultMessage(ModelAndView modelAndView, String errorMessage, String successMessage) {
        if (errorMessage != null) {
            modelAndView.addObject("successMessage", errorMessage);
        } else {            
            modelAndView.addObject("successMessage", successMessage);
        }
    }
    
    public void setUserName(ModelAndView modelAndView, Principal principal) {
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
    }
    
}
