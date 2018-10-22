package com.apu.TcpServerForAccessControlMVC.service;

import java.util.List;

public interface MVCService<S> {
    
    List<S> findById(Integer id);
    
    List<S> findByActive(Boolean active);
    
    S save(S entity);
    
}
