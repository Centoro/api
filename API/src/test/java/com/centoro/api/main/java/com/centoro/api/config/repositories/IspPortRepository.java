package com.centoro.api.repositories;

import com.centoro.api.entities.IspPort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface IspPortRepository extends JpaRepository<IspPort, Long> {
    Optional<IspPort> findByPortId(String portId);
    List<IspPort> findByStatus(String status);
    List<IspPort> findByDirection(String direction);
}