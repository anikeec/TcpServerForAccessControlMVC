package com.apu.TcpServerForAccessControlMVC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.apu.TcpServerForAccessControlDB.entity.AccessMessage;
import com.apu.TcpServerForAccessControlDB.repository.AccessMessageRepository;
import com.apu.TcpServerForAccessControlMVC.service.i.MvcReadService;

@Service
public class AccessMessageService implements MvcReadService<AccessMessage> {

    @Autowired
    private AccessMessageRepository amRepository;
    
    public Page<AccessMessage> findAll(Pageable pageable) {
        return amRepository.findAll(pageable);
    }
    
    public List<AccessMessage> findAllByPage(Integer page){
        return amRepository.findAllByPage(page);
    }

    @Override
    public List<AccessMessage> findById(Integer id) {
        throw new UnsupportedOperationException("Method has not supported yet!");
    }
    
}
