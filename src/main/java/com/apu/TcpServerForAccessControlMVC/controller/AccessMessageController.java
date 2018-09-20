package com.apu.TcpServerForAccessControlMVC.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.apu.TcpServerForAccessControlDB.entity.AccessMessage;
import com.apu.TcpServerForAccessControlDB.repository.AccessMessageRepository;

@Controller
public class AccessMessageController {
    
    @Autowired
    private AccessMessageRepository accessMessageRepository;
    
    @GetMapping("/accessMessage/view")
    public ModelAndView index(Principal principal) {
        Map<String, Object> model = new HashMap<>();
        List<AccessMessage> accessMessageList = accessMessageRepository.findAll();
        model.put("accessMessageList", accessMessageList);
        if(principal != null) {
            model.put("name", principal.getName());
        }
        return new ModelAndView("accessMessage/view", model);
    }
    
}
