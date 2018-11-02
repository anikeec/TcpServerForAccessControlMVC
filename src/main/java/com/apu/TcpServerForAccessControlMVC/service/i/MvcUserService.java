package com.apu.TcpServerForAccessControlMVC.service.i;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.apu.TcpServerForAccessControlDB.entity.SystemUser;

public interface MvcUserService extends MvcFullService<SystemUser>{

    public List<SystemUser> findAll();
    
    public List<SystemUser> findById(Integer userId);
    
    public List<SystemUser> findByActive(Boolean active);
    
    public List<SystemUser> findByEmail(String email);
    
    public SystemUser findUserByEmail(String email);
    
    public SystemUser save(SystemUser user);
    
    public void delete(SystemUser entity);

    public Page<SystemUser> findAll(Pageable pageable);

    public List<SystemUser> findAllByPage(Integer page);
    
}
