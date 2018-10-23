package com.apu.TcpServerForAccessControlMVC.service.i;

import java.util.List;

public interface MVCService<S> {
    
    List<S> findById(Integer id);
    
    List<S> findByActive(Boolean active);
    
    S save(S entity);
    
}
