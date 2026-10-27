package com.sentinelops.repository;

import com.sentinelops.entity.MonitoredService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitoredServiceRepository extends JpaRepository<MonitoredService, Long> {
    boolean existsByNameIgnoreCase(String name);
}
