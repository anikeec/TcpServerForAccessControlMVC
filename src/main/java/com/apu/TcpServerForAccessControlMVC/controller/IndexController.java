package com.apu.TcpServerForAccessControlMVC.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    
    @GetMapping("/")
    public ModelAndView index(Principal principal) {
        Map<String, Object> model = new HashMap<>();
        if(principal != null) {
            model.put("name", principal.getName());
        }
        return new ModelAndView("index", model);
    }
}
