package com.apu.TcpServerForAccessControlMVC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apu.TcpServerForAccessControlDB.entity.InfoMessage;
import com.apu.TcpServerForAccessControlDB.repository.InfoMessageRepository;

@Service
public class InfoMessageService {

    @Autowired
    private InfoMessageRepository imRepository;
    
    public List<InfoMessage> findAll() {
        return imRepository.findAll();
    }
    
}
