package com.apu.TcpServerForAccessControlMVC.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.apu.TcpServerForAccessControlDB.entity.AccessMessageWrong;
import com.apu.TcpServerForAccessControlDB.repository.AccessMessageWrongRepository;

@Controller
public class AccessMessageWrongController {
    
    @Autowired
    private AccessMessageWrongRepository accessMessageWrongRepository;
    
    @GetMapping("/accessMessageWrong/view")
    public ModelAndView index(Principal principal) {
        Map<String, Object> model = new HashMap<>();
        List<AccessMessageWrong> accessMessageWrongList = accessMessageWrongRepository.findAll();
        model.put("accessMessageWrongList", accessMessageWrongList);
        if(principal != null) {
            model.put("name", principal.getName());
        }
        return new ModelAndView("accessMessageWrong/view", model);
    }
    
}
