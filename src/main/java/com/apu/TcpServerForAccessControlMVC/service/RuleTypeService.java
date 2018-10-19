package com.apu.TcpServerForAccessControlMVC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apu.TcpServerForAccessControlDB.entity.RuleType;
import com.apu.TcpServerForAccessControlDB.repository.RuleTypeRepository;

@Service
public class RuleTypeService {

    @Autowired
    private RuleTypeRepository rtRepository;
    
    public List<RuleType> findByRuleTypeId(Integer ruleTypeId) {
        return rtRepository.findByRuleTypeId(ruleTypeId);
    }
    
    public List<RuleType> findByDescription(String description) {
        return rtRepository.findByDescription(description);
    }
    
    public List<RuleType> findAll() {
        return rtRepository.findAll();
    }
    
    public <S extends RuleType> S save(S entity) {
        return rtRepository.save(entity);
    }
    
    public void delete(RuleType entity) {
        rtRepository.delete(entity);
    }
    
}
