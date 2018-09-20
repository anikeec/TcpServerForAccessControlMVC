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
import com.apu.TcpServerForAccessControlDB.repository.CardRepository;
import com.apu.TcpServerForAccessControlDB.repository.DeviceRepository;
import com.apu.TcpServerForAccessControlDB.repository.EventTypeRepository;
import com.apu.TcpServerForAccessControlDB.repository.RuleRepository;
import com.apu.TcpServerForAccessControlDB.repository.RuleTypeRepository;

@Controller
public class RuleController {
    
    @Autowired
    private RuleRepository ruleRepository;
    
    @Autowired
    private CardRepository cardRepository;
    
    @Autowired
    private DeviceRepository deviceRepository;
    
    @Autowired
    private EventTypeRepository eventTypeRepository;
    
    @Autowired
    private RuleTypeRepository ruleTypeRepository;
    
    @GetMapping("/rule/view")
    public ModelAndView index(Principal principal) {
        Map<String, Object> model = new HashMap<>();
        List<Rule> ruleList = ruleRepository.findAll();
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
        List<Card> cardList = cardRepository.findAll();
        modelAndView.addObject("cardList", cardList);
        List<Device> deviceList = deviceRepository.findAll();
        modelAndView.addObject("deviceList", deviceList);
        List<EventType> eventTypeList = eventTypeRepository.findAll();
        modelAndView.addObject("eventTypeList", eventTypeList);
        List<RuleType> ruleTypeList = ruleTypeRepository.findAll();
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
                              ruleRepository.findByDeviceIdCardIdEventTypeIdRuleTypeId(
                              rule.getDeviceId(),
                              rule.getCardId(),
                              rule.getEventId(),
                              rule.getRuleTypeId());
        if ((ruleList != null)&&(ruleList.size() > 0)) {
            bindingResult
                    .rejectValue("cardId", "error.cardId",
                            "Rule with this parameters is already registered in the system");
        }
        List<Card> cardList = cardRepository.findAll();
        modelAndView.addObject("cardList", cardList);
        List<Device> deviceList = deviceRepository.findAll();
        modelAndView.addObject("deviceList", deviceList);
        List<EventType> eventTypeList = eventTypeRepository.findAll();
        modelAndView.addObject("eventTypeList", eventTypeList);
        List<RuleType> ruleTypeList = ruleTypeRepository.findAll();
        modelAndView.addObject("ruleTypeList", ruleTypeList);
        if (bindingResult.hasErrors()) {            
            modelAndView.setViewName("rule/add");
        } else {
            ruleRepository.save(rule);
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
        List<Rule> ruleList = ruleRepository.findByActive(false);
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
        List<Rule> ruleList = ruleRepository.findByRuleId(rule.getRuleId());
        if((ruleList == null) || (ruleList.size() == 0)) {
            bindingResult
                .rejectValue("ruleId", "error.ruleId",
                        "This rule id has't registered in the system yet");
        } else {
            rule = ruleList.get(0);
            rule.setActive(true);
            ruleRepository.save(rule);
        }            
        
        ruleList = ruleRepository.findByActive(false);
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
        List<Rule> ruleList = ruleRepository.findByActive(true);
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
        List<Rule> ruleList = ruleRepository.findByRuleId(rule.getRuleId());
        if((ruleList == null) || (ruleList.size() == 0)) {
            bindingResult
                .rejectValue("ruleId", "error.ruleId",
                        "This rule id has't registered in the system yet");
        } else {
            rule = ruleList.get(0);
            rule.setActive(false);
            ruleRepository.save(rule);
        }            
        
        ruleList = ruleRepository.findByActive(true);
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
