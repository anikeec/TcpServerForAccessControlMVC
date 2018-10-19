package com.apu.TcpServerForAccessControlMVC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apu.TcpServerForAccessControlDB.entity.EventMessage;
import com.apu.TcpServerForAccessControlDB.repository.EventMessageRepository;

@Service
public class EventMessageService {

    @Autowired
    private EventMessageRepository emRepository;
    
    public List<EventMessage> findAll(){
        return emRepository.findAll();
    }
    
}
