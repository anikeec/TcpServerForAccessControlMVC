package com.apu.TcpServerForAccessControlMVC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.apu.TcpServerForAccessControlDB.entity.EventType;
import com.apu.TcpServerForAccessControlDB.repository.EventTypeRepository;

@Service
public class EventTypeService {

    @Autowired
    private EventTypeRepository etRepository;
    
    public List<EventType> findAll() {
        return etRepository.findAll();
    }
    
    public List<EventType> findByEventId(Integer eventId) {
        return etRepository.findByEventId(eventId);
    }
    
    public List<EventType> findByDescription(String description) {
        return etRepository.findByDescription(description);
    }

    public <S extends EventType> S save(S entity) {
        return etRepository.save(entity);
    }

    public void delete(EventType entity) {
        etRepository.delete(entity);
    }
    
}
