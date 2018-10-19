package com.apu.TcpServerForAccessControlMVC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apu.TcpServerForAccessControlDB.entity.AccessMessageWrong;
import com.apu.TcpServerForAccessControlDB.repository.AccessMessageWrongRepository;

@Service
public class AccessMessageWrongService {

    @Autowired
    private AccessMessageWrongRepository amwRepository;
    
    public List<AccessMessageWrong> findAll() {
        return amwRepository.findAll();
    }
    
}
