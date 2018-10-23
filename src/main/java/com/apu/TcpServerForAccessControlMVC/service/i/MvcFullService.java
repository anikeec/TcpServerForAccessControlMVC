package com.apu.TcpServerForAccessControlMVC.service.i;

public interface MvcFullService<S> 
    extends MvcActivatableService<S>, MvcReadService<S>, MvcEditService<S> {

}
