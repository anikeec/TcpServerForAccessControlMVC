package com.apu.TcpServerForAccessControlMVC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.apu.TcpServerForAccessControlDB.entity.EventMessage;
import com.apu.TcpServerForAccessControlDB.repository.EventMessageRepository;
import com.apu.TcpServerForAccessControlMVC.service.i.MvcReadService;

@Service
public class EventMessageService implements MvcReadService<EventMessage> {

    @Autowired
    private EventMessageRepository emRepository;
    
    public List<EventMessage> findAll(){
        return emRepository.findAll();
    }

    @Override
    public Page<EventMessage> findAll(Pageable pageable) {
        return emRepository.findAll(pageable);
    }

    @Override
    public List<EventMessage> findAllByPage(Integer page) {
        throw new UnsupportedOperationException("Method has not supported yet!");
    }

    @Override
    public List<EventMessage> findById(Integer id) {
        throw new UnsupportedOperationException("Method has not supported yet!");
    }
    
}
