package com.apu.TcpServerForAccessControlMVC.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.apu.TcpServerForAccessControlDB.entity.RuleType;
import com.apu.TcpServerForAccessControlMVC.service.RuleTypeService;

@Controller
public class RuleTypeController {
    
    @Autowired
    private RuleTypeService ruleTypeService;
    
    @GetMapping("/ruleType/view")
    public ModelAndView index(Principal principal) {
        Map<String, Object> model = new HashMap<>();
        List<RuleType> ruleTypeList = ruleTypeService.findAll();
        model.put("ruleTypeList", ruleTypeList);
        if(principal != null) {
            model.put("name", principal.getName());
        }
        return new ModelAndView("ruleType/view", model);
    }
    
    @RequestMapping(value="/ruleType/add", method = RequestMethod.GET)
    public ModelAndView add(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        RuleType ruleType = new RuleType();
        modelAndView.addObject("ruleType", ruleType);
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        modelAndView.setViewName("ruleType/add");        
        return modelAndView;
    }
    
    @RequestMapping(value = "/ruleType/add", method = RequestMethod.POST)
    public ModelAndView createNewRuleType(@Valid RuleType ruleType, BindingResult bindingResult, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        List<RuleType> ruleTypeList = ruleTypeService.findByDescription(ruleType.getDescription());
        if ((ruleTypeList != null)&&(ruleTypeList.size() > 0)) {
            bindingResult
                    .rejectValue("description", "error.description",
                            "This ruleType description is already registered in the system");
        }
        if (bindingResult.hasErrors()) {            
            modelAndView.setViewName("ruleType/add");
        } else {
            ruleTypeService.save(ruleType);
            modelAndView.addObject("successMessage", "RuleType has been added successfully");
            modelAndView.addObject("ruleType", new RuleType());
            modelAndView.setViewName("ruleType/add");
        }
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        return modelAndView;
    }
    
}
