package com.apu.TcpServerForAccessControlMVC.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.apu.TcpServerForAccessControlDB.entity.EventMessage;
import com.apu.TcpServerForAccessControlMVC.service.EventMessageService;
import com.apu.TcpServerForAccessControlMVC.service.utils.PageableServiceUtils;

@Controller
public class EventMessageController {
    
//    @Autowired
//    private EventMessageService eventMessageService;
    
    @Autowired
    @Qualifier("eventMessageServiceUtils")
    private PageableServiceUtils<EventMessage> utils;
    
    @GetMapping("/eventMessage/view")
    public ModelAndView index(Principal principal,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer pageSize) {
        ModelAndView modelAndView = new ModelAndView();
        utils.setPages(modelAndView, page, pageSize);
        utils.setUserName(modelAndView, principal);
        modelAndView.setViewName("eventMessage/view");    
        return modelAndView;
    }
    
}
