package com.apu.TcpServerForAccessControlMVC.redis;

public interface MessagePublisher {

    void publish(final String message);
    
}
