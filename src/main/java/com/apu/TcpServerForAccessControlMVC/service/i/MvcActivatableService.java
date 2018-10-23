package com.apu.TcpServerForAccessControlMVC.service.i;

import java.util.List;

public interface MvcActivatableService<S> {

    List<S> findByActive(Boolean active);
    
}
