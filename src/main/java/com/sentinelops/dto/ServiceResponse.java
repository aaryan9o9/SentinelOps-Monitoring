package com.sentinelops.dto;

import com.sentinelops.entity.MonitoredService;

import java.time.Instant;

public record ServiceResponse(
        Long id,
        String name,
        String description,
        String healthStatus,
        Instant createdAt
) {

    public static ServiceResponse from(MonitoredService service) {

        return new ServiceResponse(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getHealthStatus(),
                service.getCreatedAt()
        );
    }
}