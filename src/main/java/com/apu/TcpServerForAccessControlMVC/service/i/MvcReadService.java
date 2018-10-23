package com.apu.TcpServerForAccessControlMVC.service.i;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MvcReadService<S> {
    
    public Page<S> findAll(Pageable pageable);
    
    public List<S> findAllByPage(Integer page);
    
    List<S> findById(Integer id);
    
}
