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
import com.apu.TcpServerForAccessControlMVC.service.ServiceUtils;

@Controller
public class DeviceController {
    
    @Autowired
    private DeviceService deviceService;
    
    @Autowired
    @Qualifier("deviceServiceUtils")
    private ServiceUtils<Device> utils;
    
    @GetMapping("/device/view")
    public ModelAndView index(Principal principal) {
        Map<String, Object> model = new HashMap<>();
        List<Device> deviceList = deviceService.findAll();
        model.put("deviceList", deviceList);
        if(principal != null) {
            model.put("name", principal.getName());
        }
        return new ModelAndView("device/view", model);
    }
    
    @RequestMapping(value="/device/add", method = RequestMethod.GET)
    public ModelAndView add(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        Device device = new Device();
        modelAndView.addObject("device", device);
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
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
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        return modelAndView;
    }
    
    @RequestMapping(value="/device/activate", method = RequestMethod.GET)
    public ModelAndView activate(Principal principal) {
        ModelAndView modelAndView = utils.FillMvcEntity("Device", "Activate device", "/device/activate", false);  
        utils.setUserName(modelAndView, principal);
        return modelAndView;
    }
    
    @RequestMapping(value = "/device/activate", method = RequestMethod.POST)
    public ModelAndView activate(@Valid VisualEntity entity, BindingResult bindingResult, Principal principal) {
        Assert.notNull(entity.getEntityId(), "DeviceId has not be null.");     
        String errorMessage = utils.saveEntity(entity, true);
        ModelAndView modelAndView = utils.FillMvcEntity("Device", "Activate device", "/device/activate", false);
        if (errorMessage != null) {
            modelAndView.addObject("successMessage", errorMessage);
        } else {            
            modelAndView.addObject("successMessage", "Device has been activated successfully");
        }
        utils.setUserName(modelAndView, principal);
        return modelAndView;
    }
    
    @RequestMapping(value="/device/inactivate", method = RequestMethod.GET)
    public ModelAndView inactivate(Principal principal) {
        ModelAndView modelAndView = utils.FillMvcEntity("Device", "Inactivate device", "/device/inactivate", true);
        utils.setUserName(modelAndView, principal);
        return modelAndView;
    }
    
    @RequestMapping(value = "/device/inactivate", method = RequestMethod.POST)
    public ModelAndView inactivateCard(@Valid VisualEntity entity, BindingResult bindingResult, Principal principal) {
        Assert.notNull(entity.getEntityId(), "DeviceId has not be null.");        
        String errorMessage = utils.saveEntity(entity, false);         
        ModelAndView modelAndView = utils.FillMvcEntity("Device", "Inactivate device", "/device/inactivate", true);
        if (errorMessage != null) {
            modelAndView.addObject("successMessage", errorMessage);
        } else {            
            modelAndView.addObject("successMessage", "Device has been inactivated successfully");
        }
        utils.setUserName(modelAndView, principal);
        return modelAndView;
    }
//    public ModelAndView inactivate(@Valid Device device, BindingResult bindingResult, Principal principal) {
//        ModelAndView modelAndView = new ModelAndView();        
//        List<Device> deviceList = deviceService.findById(device.getDeviceId());
//        if((deviceList == null) || (deviceList.size() == 0)) {
//            bindingResult
//                .rejectValue("deviceId", "error.deviceId",
//                        "This device id has't registered in the system yet");
//        } else {
//            device = deviceList.get(0);
//            device.setActive(false);
//            deviceService.save(device);
//        }            
//        
//        deviceList = deviceService.findByActive(true);
//        modelAndView.addObject("deviceList", deviceList);
//        if (bindingResult.hasErrors()) {            
//            modelAndView.setViewName("device/inactivate");
//        } else {            
//            modelAndView.addObject("successMessage", "Device has been inactivated successfully");
//            modelAndView.addObject("device", new Device());
//            modelAndView.setViewName("device/inactivate");
//        }
//        if(principal != null) {
//            modelAndView.addObject("name", principal.getName());
//        }
//        return modelAndView;
//    }
    
}
