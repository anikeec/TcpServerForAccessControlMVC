package com.apu.TcpServerForAccessControlMVC.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.apu.TcpServerForAccessControlDB.entity.Card;
import com.apu.TcpServerForAccessControlDB.entity.Device;
import com.apu.TcpServerForAccessControlMVC.service.CardService;
import com.apu.TcpServerForAccessControlMVC.service.DeviceService;

@Controller
public class IndexController {
    
    @Autowired
    private CardService cardService;
    
    @Autowired
    private DeviceService deviceService;
    
    @GetMapping("/")
    public ModelAndView index() {
        Map<String, Object> model = new HashMap<>();
        model.put("name", "Friend");
        List<Card> cardList = cardService.findAll();
        model.put("cardList", cardList);
        List<Device> deviceList = deviceService.findAll();
        model.put("deviceList", deviceList);
//        List<AccessMessage> amList = accessMessageService.findAll();
//        model.put("amList", amList);
        return new ModelAndView("index", model);
    }
}
