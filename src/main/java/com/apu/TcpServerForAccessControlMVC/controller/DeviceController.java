package com.apu.TcpServerForAccessControlMVC.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.apu.TcpServerForAccessControlDB.entity.Device;
import com.apu.TcpServerForAccessControlMVC.entity.VisualEntity;
import com.apu.TcpServerForAccessControlMVC.service.DeviceService;
import com.apu.TcpServerForAccessControlMVC.service.utils.ActivatableServiceUtils;

@Controller
public class DeviceController {
    
    @Autowired
    private DeviceService deviceService;
    
    @Autowired
    @Qualifier("deviceServiceUtils")
    private ActivatableServiceUtils<Device> utils;
    
    @GetMapping("/device/view")
    public ModelAndView index(Principal principal) {
        List<Device> deviceList = deviceService.findAll();            
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("deviceList", deviceList);
        utils.setUserName(modelAndView, principal);
        modelAndView.setViewName("device/view");      
        return modelAndView;
    }
    
    @RequestMapping(value="/device/add", method = RequestMethod.GET)
    public ModelAndView add(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        Device device = new Device();
        modelAndView.addObject("device", device);
        utils.setUserName(modelAndView, principal);
        modelAndView.setViewName("device/add");        
        return modelAndView;
    }
    
    @RequestMapping(value = "/device/add", method = RequestMethod.POST)
    public ModelAndView createNewDevice(@Valid Device device, BindingResult bindingResult, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        List<Device> deviceList = deviceService.findByDeviceNumber(device.getDeviceNumber());
        if ((deviceList != null)&&(deviceList.size() > 0)) {
            bindingResult
                    .rejectValue("deviceNumber", "error.deviceNumber",
                            "This device number is already registered in the system");
        }
        if (bindingResult.hasErrors()) {            
            modelAndView.setViewName("device/add");
        } else {
            deviceService.save(device);
            modelAndView.addObject("successMessage", "Device has been added successfully");
            modelAndView.addObject("device", new Device());
            modelAndView.setViewName("device/add");
        }
        utils.setUserName(modelAndView, principal);
        return modelAndView;
    }
    
    @RequestMapping(value="/device/activate", method = RequestMethod.GET)
    public ModelAndView activate(Principal principal) {
        ModelAndView modelAndView = utils.fillMvcEntity("Device", "Activate device", "/device/activate", false);  
        utils.setUserName(modelAndView, principal);
        return modelAndView;
    }
    
    @RequestMapping(value = "/device/activate", method = RequestMethod.POST)
    public ModelAndView activate(@Valid VisualEntity entity, BindingResult bindingResult, Principal principal) {
        Assert.notNull(entity.getEntityId(), "DeviceId has not be null.");     
        String errorMessage = utils.saveEntity(entity, true);
        ModelAndView modelAndView = utils.fillMvcEntity("Device", "Activate device", "/device/activate", false);
        utils.setResultMessage(modelAndView, errorMessage, "Device has been activated successfully");
        utils.setUserName(modelAndView, principal);
        return modelAndView;
    }
    
    @RequestMapping(value="/device/inactivate", method = RequestMethod.GET)
    public ModelAndView inactivate(Principal principal) {
        ModelAndView modelAndView = utils.fillMvcEntity("Device", "Inactivate device", "/device/inactivate", true);
        utils.setUserName(modelAndView, principal);
        return modelAndView;
    }
    
    @RequestMapping(value = "/device/inactivate", method = RequestMethod.POST)
    public ModelAndView inactivateCard(@Valid VisualEntity entity, BindingResult bindingResult, Principal principal) {
        Assert.notNull(entity.getEntityId(), "DeviceId has not be null.");        
        String errorMessage = utils.saveEntity(entity, false);         
        ModelAndView modelAndView = utils.fillMvcEntity("Device", "Inactivate device", "/device/inactivate", true);
        utils.setResultMessage(modelAndView, errorMessage, "Device has been inactivated successfully");
        utils.setUserName(modelAndView, principal);
        return modelAndView;
    }
    
}
