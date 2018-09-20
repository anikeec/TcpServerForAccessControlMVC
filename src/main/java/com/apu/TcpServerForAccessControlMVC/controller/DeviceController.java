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

import com.apu.TcpServerForAccessControlDB.entity.Device;
import com.apu.TcpServerForAccessControlDB.repository.DeviceRepository;

@Controller
public class DeviceController {
    
    @Autowired
    private DeviceRepository deviceRepository;
    
    @GetMapping("/device/view")
    public ModelAndView index(Principal principal) {
        Map<String, Object> model = new HashMap<>();
        List<Device> deviceList = deviceRepository.findAll();
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
        List<Device> deviceList = deviceRepository.findByDeviceNumber(device.getDeviceNumber());
        if ((deviceList != null)&&(deviceList.size() > 0)) {
            bindingResult
                    .rejectValue("deviceNumber", "error.deviceNumber",
                            "This device number is already registered in the system");
        }
        if (bindingResult.hasErrors()) {            
            modelAndView.setViewName("device/add");
        } else {
            deviceRepository.save(device);
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
    public ModelAndView activateDevice(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        Device device = new Device();
        modelAndView.addObject("device", device);
        List<Device> deviceList = deviceRepository.findByActive(false);
        modelAndView.addObject("deviceList", deviceList);
        modelAndView.setViewName("device/activate"); 
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        return modelAndView;
    }
    
    @RequestMapping(value = "/device/activate", method = RequestMethod.POST)
    public ModelAndView activateDevice(@Valid Device device, BindingResult bindingResult, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();        
        List<Device> deviceList = deviceRepository.findByDeviceId(device.getDeviceId());
        if((deviceList == null) || (deviceList.size() == 0)) {
            bindingResult
                .rejectValue("deviceId", "error.deviceId",
                        "This device id has't registered in the system yet");
        } else {
            device = deviceList.get(0);
            device.setActive(true);
            deviceRepository.save(device);
        }            
        
        deviceList = deviceRepository.findByActive(false);
        modelAndView.addObject("deviceList", deviceList);
        if (bindingResult.hasErrors()) {            
            modelAndView.setViewName("device/activate");
        } else {            
            modelAndView.addObject("successMessage", "Device has been activated successfully");
            modelAndView.addObject("device", new Device());
            modelAndView.setViewName("device/activate");
        }
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        return modelAndView;
    }
    
    @RequestMapping(value="/device/inactivate", method = RequestMethod.GET)
    public ModelAndView inactivateDevice(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        Device device = new Device();
        modelAndView.addObject("device", device);
        List<Device> deviceList = deviceRepository.findByActive(true);
        modelAndView.addObject("deviceList", deviceList);
        modelAndView.setViewName("device/inactivate");
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        return modelAndView;
    }
    
    @RequestMapping(value = "/device/inactivate", method = RequestMethod.POST)
    public ModelAndView inactivateDevice(@Valid Device device, BindingResult bindingResult, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();        
        List<Device> deviceList = deviceRepository.findByDeviceId(device.getDeviceId());
        if((deviceList == null) || (deviceList.size() == 0)) {
            bindingResult
                .rejectValue("deviceId", "error.deviceId",
                        "This device id has't registered in the system yet");
        } else {
            device = deviceList.get(0);
            device.setActive(false);
            deviceRepository.save(device);
        }            
        
        deviceList = deviceRepository.findByActive(true);
        modelAndView.addObject("deviceList", deviceList);
        if (bindingResult.hasErrors()) {            
            modelAndView.setViewName("device/inactivate");
        } else {            
            modelAndView.addObject("successMessage", "Device has been inactivated successfully");
            modelAndView.addObject("device", new Device());
            modelAndView.setViewName("device/inactivate");
        }
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        return modelAndView;
    }
    
}
