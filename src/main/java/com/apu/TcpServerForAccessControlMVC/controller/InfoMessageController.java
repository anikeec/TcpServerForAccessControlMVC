package com.apu.TcpServerForAccessControlMVC.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.apu.TcpServerForAccessControlDB.entity.InfoMessage;
import com.apu.TcpServerForAccessControlDB.repository.InfoMessageRepository;

@Controller
public class InfoMessageController {
    
    @Autowired
    private InfoMessageRepository infoMessageRepository;
    
    @GetMapping("/infoMessage/view")
    public ModelAndView index(Principal principal) {
        Map<String, Object> model = new HashMap<>();
        List<InfoMessage> infoMessageList = infoMessageRepository.findAll();
        model.put("infoMessageList", infoMessageList);
        if(principal != null) {
            model.put("name", principal.getName());
        }
        return new ModelAndView("infoMessage/view", model);
    }
    
}
