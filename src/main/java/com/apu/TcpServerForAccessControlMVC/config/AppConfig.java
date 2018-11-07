package com.apu.TcpServerForAccessControlMVC.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.apu.TcpServerForAccessControlDB.entity.AccessMessage;
import com.apu.TcpServerForAccessControlDB.entity.Card;
import com.apu.TcpServerForAccessControlDB.entity.Device;
import com.apu.TcpServerForAccessControlDB.entity.EventMessage;
import com.apu.TcpServerForAccessControlMVC.service.AccessMessageService;
import com.apu.TcpServerForAccessControlMVC.service.CardService;
import com.apu.TcpServerForAccessControlMVC.service.DeviceService;
import com.apu.TcpServerForAccessControlMVC.service.EventMessageService;
import com.apu.TcpServerForAccessControlMVC.service.utils.ActivatableServiceUtils;
import com.apu.TcpServerForAccessControlMVC.service.utils.PageableServiceUtils;
import com.apu.TcpServerForAccessControlMVC.service.utils.ServiceUtils; 

@Configuration
@ComponentScan({"com.apu.TcpServerForAccessControlDB","com.apu.TcpServerForAccessControlMVC"})
public class AppConfig {
    
    @Bean(name = "accessMessageServiceUtils")
    PageableServiceUtils<AccessMessage> accessMessageServiceUtils(AccessMessageService amService) {
        return new PageableServiceUtils<AccessMessage>(amService);
    }
    
    @Bean(name = "eventMessageServiceUtils")
    PageableServiceUtils<EventMessage> eventMessageServiceUtils(EventMessageService emService) {
        return new PageableServiceUtils<EventMessage>(emService);
    }
    
    @Bean(name = "cardServiceUtils")
    ActivatableServiceUtils<Card> cardServiceUtils(CardService service) {
        return new ActivatableServiceUtils<Card>(service);
    }
    
    @Bean(name = "deviceServiceUtils")
    ActivatableServiceUtils<Device> deviceServiceUtils(DeviceService service) {
        return new ActivatableServiceUtils<Device>(service);
    }
    
}
