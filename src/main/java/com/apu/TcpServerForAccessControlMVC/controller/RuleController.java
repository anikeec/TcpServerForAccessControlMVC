package com.apu.TcpServerForAccessControlMVC.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.apu.TcpServerForAccessControlDB.entity.Card;
import com.apu.TcpServerForAccessControlDB.entity.Device;
import com.apu.TcpServerForAccessControlDB.entity.EventType;
import com.apu.TcpServerForAccessControlDB.entity.Rule;
import com.apu.TcpServerForAccessControlDB.entity.RuleType;
import com.apu.TcpServerForAccessControlMVC.service.CardService;
import com.apu.TcpServerForAccessControlMVC.service.DeviceService;
import com.apu.TcpServerForAccessControlMVC.service.EventTypeService;
import com.apu.TcpServerForAccessControlMVC.service.RuleService;
import com.apu.TcpServerForAccessControlMVC.service.RuleTypeService;

@Controller
public class RuleController {
    
    @Autowired
    private RuleService ruleService;
    
    @Autowired
    private CardService cardService;
    
    @Autowired
    private DeviceService deviceService;
    
    @Autowired
    private EventTypeService eventTypeService;
    
    @Autowired
    private RuleTypeService ruleTypeService;
    
    @GetMapping("/rule/view")
    public ModelAndView index(Principal principal) {
        Map<String, Object> model = new HashMap<>();
        List<Rule> ruleList = ruleService.findAll();
        model.put("ruleList", ruleList);
        if(principal != null) {
            model.put("name", principal.getName());
        }
        return new ModelAndView("rule/view", model);
    }
    
    @RequestMapping(value="/rule/add", method = RequestMethod.GET)
    public ModelAndView add(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        Rule rule = new Rule();
        modelAndView.addObject("rule", rule);
        List<Card> cardList = cardService.findAll();
        modelAndView.addObject("cardList", cardList);
        List<Device> deviceList = deviceService.findAll();
        modelAndView.addObject("deviceList", deviceList);
        List<EventType> eventTypeList = eventTypeService.findAll();
        modelAndView.addObject("eventTypeList", eventTypeList);
        List<RuleType> ruleTypeList = ruleTypeService.findAll();
        modelAndView.addObject("ruleTypeList", ruleTypeList);
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        modelAndView.setViewName("rule/add");        
        return modelAndView;
    }
    
    @RequestMapping(value = "/rule/add", method = RequestMethod.POST)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    public ModelAndView createNewRule(@Valid Rule rule, BindingResult bindingResult, Principal principal
//            @RequestParam ("dateBegin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateBegin,
//            @RequestParam ("dateEnd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateEnd
            ) {
        ModelAndView modelAndView = new ModelAndView();
        List<Rule> ruleList = 
                              ruleService.findByDeviceIdCardIdEventTypeIdRuleTypeId(
                              rule.getDeviceId(),
                              rule.getCardId(),
                              rule.getEventId(),
                              rule.getRuleTypeId());
        if ((ruleList != null)&&(ruleList.size() > 0)) {
            bindingResult
                    .rejectValue("cardId", "error.cardId",
                            "Rule with this parameters is already registered in the system");
        }
        List<Card> cardList = cardService.findAll();
        modelAndView.addObject("cardList", cardList);
        List<Device> deviceList = deviceService.findAll();
        modelAndView.addObject("deviceList", deviceList);
        List<EventType> eventTypeList = eventTypeService.findAll();
        modelAndView.addObject("eventTypeList", eventTypeList);
        List<RuleType> ruleTypeList = ruleTypeService.findAll();
        modelAndView.addObject("ruleTypeList", ruleTypeList);
        if (bindingResult.hasErrors()) {            
            modelAndView.setViewName("rule/add");
        } else {
            ruleService.save(rule);
            modelAndView.addObject("successMessage", "Rule has been added successfully");
            modelAndView.addObject("rule", new Rule());
            modelAndView.setViewName("rule/add");
        }
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        return modelAndView;
    }
    
    @RequestMapping(value="/rule/activate", method = RequestMethod.GET)
    public ModelAndView activateRule(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        Rule rule = new Rule();
        modelAndView.addObject("rule", rule);
        List<Rule> ruleList = ruleService.findByActive(false);
        modelAndView.addObject("ruleList", ruleList);
        modelAndView.setViewName("rule/activate"); 
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        return modelAndView;
    }
    
    @RequestMapping(value = "/rule/activate", method = RequestMethod.POST)
    public ModelAndView activateRule(@Valid Rule rule, BindingResult bindingResult, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();        
        List<Rule> ruleList = ruleService.findByRuleId(rule.getRuleId());
        if((ruleList == null) || (ruleList.size() == 0)) {
            bindingResult
                .rejectValue("ruleId", "error.ruleId",
                        "This rule id has't registered in the system yet");
        } else {
            rule = ruleList.get(0);
            rule.setActive(true);
            ruleService.save(rule);
        }            
        
        ruleList = ruleService.findByActive(false);
        modelAndView.addObject("ruleList", ruleList);
        if (bindingResult.hasErrors()) {            
            modelAndView.setViewName("rule/activate");
        } else {            
            modelAndView.addObject("successMessage", "Rule has been activated successfully");
            modelAndView.addObject("rule", new Rule());
            modelAndView.setViewName("rule/activate");
        }
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        return modelAndView;
    }
    
    @RequestMapping(value="/rule/inactivate", method = RequestMethod.GET)
    public ModelAndView inactivateRule(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        Rule rule = new Rule();
        modelAndView.addObject("rule", rule);
        List<Rule> ruleList = ruleService.findByActive(true);
        modelAndView.addObject("ruleList", ruleList);
        modelAndView.setViewName("rule/inactivate");
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        return modelAndView;
    }
    
    @RequestMapping(value = "/rule/inactivate", method = RequestMethod.POST)
    public ModelAndView inactivateRule(@Valid Rule rule, BindingResult bindingResult, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();        
        List<Rule> ruleList = ruleService.findByRuleId(rule.getRuleId());
        if((ruleList == null) || (ruleList.size() == 0)) {
            bindingResult
                .rejectValue("ruleId", "error.ruleId",
                        "This rule id has't registered in the system yet");
        } else {
            rule = ruleList.get(0);
            rule.setActive(false);
            ruleService.save(rule);
        }            
        
        ruleList = ruleService.findByActive(true);
        modelAndView.addObject("ruleList", ruleList);
        if (bindingResult.hasErrors()) {            
            modelAndView.setViewName("rule/inactivate");
        } else {            
            modelAndView.addObject("successMessage", "Rule has been inactivated successfully");
            modelAndView.addObject("rule", new Rule());
            modelAndView.setViewName("rule/inactivate");
        }
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        return modelAndView;
    }
    
}
