package com.apu.TcpServerForAccessControlMVC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apu.TcpServerForAccessControlDB.entity.Card;
import com.apu.TcpServerForAccessControlDB.entity.Device;
import com.apu.TcpServerForAccessControlDB.entity.EventType;
import com.apu.TcpServerForAccessControlDB.entity.Rule;
import com.apu.TcpServerForAccessControlDB.entity.RuleType;
import com.apu.TcpServerForAccessControlDB.repository.RuleRepository;

@Service
public class RuleService {

    @Autowired
    private RuleRepository ruleRepository;
    
    public List<Rule> findAll() {
        return ruleRepository.findAll();
    }
    
    public List<Rule> findByRuleId(Integer ruleId) {
        return ruleRepository.findByRuleId(ruleId);
    }
  
    public List<Rule> findByActive(Boolean active) {
        return ruleRepository.findByActive(active);
    }
  
    public List<Rule> findByDeviceIdAndCardId(Device deviceId, Card cardId) {
        return ruleRepository.findByDeviceIdAndCardId(deviceId, cardId);
    }
  
    public Integer findByCardDeviceEvent(String cardNumber,
                                    Integer deviceNumber,
                                    Integer eventId) {
        return ruleRepository.findByCardDeviceEvent(cardNumber, deviceNumber, eventId);
    }
      
    public List<Rule> findByDeviceIdCardIdEventTypeIdRuleTypeId(Device deviceId,
                                                            Card cardId,
                                                            EventType eventTypeId,
                                                            RuleType ruleTypeId) {
        return ruleRepository.findByDeviceIdCardIdEventTypeIdRuleTypeId(deviceId, cardId, eventTypeId, ruleTypeId);
    } 
  
    public <S extends Rule> S save(S entity) {
        return ruleRepository.save(entity);
    }
  
    public void delete(Rule entity) {
        ruleRepository.delete(entity);
    }
    
}
