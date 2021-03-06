package com.apu.TcpServerForAccessControlMVC.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.apu.TcpServerForAccessControlDB.entity.AccessMessage;
import com.apu.TcpServerForAccessControlMVC.service.AccessMessageService;
import com.apu.TcpServerForAccessControlMVC.service.utils.PageableServiceUtils;

@Controller
public class AccessMessageController {
    
//    @Autowired
//    private AccessMessageService accessMessageService;
    
    @Autowired
    @Qualifier("accessMessageServiceUtils")
    private PageableServiceUtils<AccessMessage> utils;    
    
    
    @GetMapping("/accessMessage/view")
    public ModelAndView index(Principal principal,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer pageSize) {
        ModelAndView modelAndView = new ModelAndView();
        utils.setPages(modelAndView, page, pageSize);
        utils.setUserName(modelAndView, principal);
        modelAndView.setViewName("accessMessage/view");    
        return modelAndView;
    }
    
//    @GetMapping("/accessMessage/view")
//    public ModelAndView index(Principal principal, @RequestParam(value = "page", required = false) Integer page) {
//        Map<String, Object> model = new HashMap<>();
//        List<AccessMessage> accessMessageList = null;
//        List<Integer> pageList = new ArrayList<>();
//        pageList.add(5);
//        if(page == null) {
//            accessMessageList = accessMessageService.findAll();
//        } else {
////            accessMessageList = accessMessageService.findAllByPage(page);
//            accessMessageList = accessMessageService.findAll();
//            int size = accessMessageList.size();
//            List<AccessMessage> retList = new ArrayList<>();
//            int start = page*PAGE_SIZE;
//            if(size > start) {
//                int amount = size % PAGE_SIZE;                
//                for(int i=0; i<amount; i++) {
//                    retList.add(accessMessageList.get(start + i));
//                }
//            }
//            accessMessageList = retList;
//        }
//        model.put("accessMessageList", accessMessageList);
//        if(principal != null) {
//            model.put("name", principal.getName());
//        }
//        
//        Pageable pageable = new Pageable();
//        Page<AccessMessage> pagePageList = new PageImpl<AccessMessage>(accessMessageList);;
//        PageWrapper<AccessMessage> pageWrapper = new PageWrapper<>(pagePageList, "");
//
//        return new ModelAndView("accessMessage/view", model)
////                .addObject("pageList", pageList)
//                .addObject("page", pageWrapper);
//    }
    
}
