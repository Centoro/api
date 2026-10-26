package com.centoro.api.repositories;

import com.centoro.api.entities.ConnectedDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ConnectedDeviceRepository extends JpaRepository<ConnectedDevice, Long> {
    
    Optional<ConnectedDevice> findByDeviceId(String deviceId);
    List<ConnectedDevice> findByStatus(String status);
    List<ConnectedDevice> findByUser(String user);
}