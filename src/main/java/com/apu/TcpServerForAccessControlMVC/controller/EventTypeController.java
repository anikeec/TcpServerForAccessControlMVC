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

import com.apu.TcpServerForAccessControlDB.entity.EventType;
import com.apu.TcpServerForAccessControlDB.repository.EventTypeRepository;

@Controller
public class EventTypeController {
    
    @Autowired
    private EventTypeRepository eventTypeRepository;
    
    @GetMapping("/eventType/view")
    public ModelAndView index(Principal principal) {
        Map<String, Object> model = new HashMap<>();
        List<EventType> eventTypeList = eventTypeRepository.findAll();
        model.put("eventTypeList", eventTypeList);
        if(principal != null) {
            model.put("name", principal.getName());
        }
        return new ModelAndView("eventType/view", model);
    }
    
    @RequestMapping(value="/eventType/add", method = RequestMethod.GET)
    public ModelAndView add(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        EventType eventType = new EventType();
        modelAndView.addObject("eventType", eventType);
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        modelAndView.setViewName("eventType/add");        
        return modelAndView;
    }
    
    @RequestMapping(value = "/eventType/add", method = RequestMethod.POST)
    public ModelAndView createNewEventType(@Valid EventType eventType, BindingResult bindingResult, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        List<EventType> eventTypeList = eventTypeRepository.findByDescription(eventType.getDescription());
        if ((eventTypeList != null)&&(eventTypeList.size() > 0)) {
            bindingResult
                    .rejectValue("description", "error.description",
                            "This eventType description is already registered in the system");
        }
        if (bindingResult.hasErrors()) {            
            modelAndView.setViewName("eventType/add");
        } else {
            eventTypeRepository.save(eventType);
            modelAndView.addObject("successMessage", "EventType has been added successfully");
            modelAndView.addObject("eventType", new EventType());
            modelAndView.setViewName("eventType/add");
        }
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        return modelAndView;
    }
    
}
