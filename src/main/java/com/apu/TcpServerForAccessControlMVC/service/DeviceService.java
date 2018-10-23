package com.apu.TcpServerForAccessControlMVC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apu.TcpServerForAccessControlDB.entity.Device;
import com.apu.TcpServerForAccessControlDB.repository.DeviceRepository;
import com.apu.TcpServerForAccessControlMVC.service.i.MvcFullService;

@Service
public class DeviceService implements MvcFullService<Device> {

    @Autowired
    private DeviceRepository deviceRepository;
    
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }
    
    public List<Device> findByActive(Boolean active) {
        return deviceRepository.findByActive(active);
    }
    
    public List<Device> findByDeviceNumber(Integer deviceNumber) {
        return deviceRepository.findByDeviceNumber(deviceNumber);
    }
    
    @Override
    public List<Device> findById(Integer deviceId) {
        return deviceRepository.findByDeviceId(deviceId);
    }    
    
    @Override
    public Device save(Device entity) {
        return deviceRepository.save(entity);
    }

    public void delete(Device entity) {
        deviceRepository.delete(entity);
    }
    
}
