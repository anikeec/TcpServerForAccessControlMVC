package com.apu.TcpServerForAccessControlMVC.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.apu.TcpServerForAccessControlDB.entity.SystemUser;
import com.apu.TcpServerForAccessControlDB.repository.SystemUserRepository;

public class SystemUserRepositoryMock implements SystemUserRepository {
    
    private List<SystemUser> userList = new ArrayList<>();

    @Override
    public <S extends SystemUser> Iterable<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException("Not implemented, yet");
    }

    @Override
    public Optional<SystemUser> findById(Integer id) {
        throw new UnsupportedOperationException("Not implemented, yet");
    }

    @Override
    public boolean existsById(Integer id) {
        throw new UnsupportedOperationException("Not implemented, yet");
    }

    @Override
    public Iterable<SystemUser> findAllById(Iterable<Integer> ids) {
        throw new UnsupportedOperationException("Not implemented, yet");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Not implemented, yet");
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("Not implemented, yet");        
    }

    @Override
    public void deleteAll(Iterable<? extends SystemUser> entities) {
        throw new UnsupportedOperationException("Not implemented, yet");        
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not implemented, yet");
    }

    @Override
    public List<SystemUser> findAll() {
        throw new UnsupportedOperationException("Not implemented, yet");
    }

    @Override
    public List<SystemUser> findByEmail(String email) {
        throw new UnsupportedOperationException("Not implemented, yet");
    }

    @Override
    public List<SystemUser> findByUserId(Integer userId) {
        throw new UnsupportedOperationException("Not implemented, yet");
    }

    @Override
    public List<SystemUser> findByActive(Boolean active) {
        throw new UnsupportedOperationException("Not implemented, yet");
    }

    @Override
    public <S extends SystemUser> S save(S entity) {
        throw new UnsupportedOperationException("Not implemented, yet");
    }

    @Override
    public void delete(SystemUser entity) {
        throw new UnsupportedOperationException("Not implemented, yet");
    }

}
