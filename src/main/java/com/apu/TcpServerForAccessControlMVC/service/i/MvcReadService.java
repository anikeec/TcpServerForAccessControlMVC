package com.apu.TcpServerForAccessControlMVC.service.i;

import java.util.List;

public interface MvcReadService<S> {
    
    List<S> findById(Integer id);
    
}
