package com.apu.TcpServerForAccessControlMVC.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.apu.TcpServerForAccessControlDB.entity.AccessMessage;
import com.apu.TcpServerForAccessControlDB.repository.AccessMessageRepository;

@Controller
public class AccessMessageController {
    
    @Autowired
    private AccessMessageRepository accessMessageRepository;
    
    @GetMapping("/accessMessage/view")
    public ModelAndView index(Principal principal, @RequestParam(value = "page", required = false) Integer page) {
        Map<String, Object> model = new HashMap<>();
        List<AccessMessage> accessMessageList = null;
        if(page == null) {
            accessMessageList = accessMessageRepository.findAll();
        } else {
            accessMessageList = accessMessageRepository.findAllByPage(page);
        }
        model.put("accessMessageList", accessMessageList);
        if(principal != null) {
            model.put("name", principal.getName());
        }
        return new ModelAndView("accessMessage/view", model);
    }
    
}
