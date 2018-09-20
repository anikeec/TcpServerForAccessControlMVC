package com.apu.TcpServerForAccessControlMVC.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.apu.TcpServerForAccessControlDB.entity.EventMessage;
import com.apu.TcpServerForAccessControlDB.repository.EventMessageRepository;

@Controller
public class EventMessageController {
    
    @Autowired
    private EventMessageRepository eventMessageRepository;
    
    @GetMapping("/eventMessage/view")
    public ModelAndView index(Principal principal) {
        Map<String, Object> model = new HashMap<>();
        List<EventMessage> eventMessageList = eventMessageRepository.findAll();
        model.put("eventMessageList", eventMessageList);
        if(principal != null) {
            model.put("name", principal.getName());
        }
        return new ModelAndView("eventMessage/view", model);
    }
    
}