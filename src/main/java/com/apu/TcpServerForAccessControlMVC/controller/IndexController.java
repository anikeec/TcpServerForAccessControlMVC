package com.apu.TcpServerForAccessControlMVC.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.apu.TcpServerForAccessControlDB.entity.AccessMessage;
import com.apu.TcpServerForAccessControlDB.entity.Card;
import com.apu.TcpServerForAccessControlDB.entity.Device;
import com.apu.TcpServerForAccessControlDB.repository.AccessMessageRepository;
import com.apu.TcpServerForAccessControlDB.repository.CardRepository;
import com.apu.TcpServerForAccessControlDB.repository.DeviceRepository;

@Controller
public class IndexController {
    
    @Autowired
    private CardRepository cardRepository;
    
    @Autowired
    private DeviceRepository deviceRepository;
    
    @GetMapping("/")
    public ModelAndView index() {
        Map<String, Object> model = new HashMap<>();
        model.put("name", "Friend");
        List<Card> cardList = cardRepository.findAll();
        model.put("cardList", cardList);
        List<Device> deviceList = deviceRepository.findAll();
        model.put("deviceList", deviceList);
//        List<AccessMessage> amList = accessMessageRepository.findAll();
//        model.put("amList", amList);
        return new ModelAndView("index", model);
    }
}
