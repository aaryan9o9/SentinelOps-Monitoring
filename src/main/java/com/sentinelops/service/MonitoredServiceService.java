package com.sentinelops.service;

import com.sentinelops.dto.CreateServiceRequest;
import com.sentinelops.dto.ServiceResponse;
import com.sentinelops.entity.MonitoredService;
import com.sentinelops.exception.ResourceNotFoundException;
import com.sentinelops.repository.MonitoredServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MonitoredServiceService {

    private final MonitoredServiceRepository repository;

    public MonitoredServiceService(MonitoredServiceRepository repository) {
        this.repository = repository;
    }

    /**
     * Create a new monitored service.
     */
    @Transactional
    public ServiceResponse create(CreateServiceRequest request) {

        if (repository.existsByNameIgnoreCase(request.name())) {
            throw new IllegalArgumentException(
                    "A monitored service with this name already exists"
            );
        }

        MonitoredService service = new MonitoredService(
                request.name().trim(),
                request.description()
        );

        MonitoredService savedService = repository.save(service);

        return ServiceResponse.from(savedService);
    }

    /**
     * Retrieve all monitored services.
     */
    @Transactional(readOnly = true)
    public List<ServiceResponse> getAll() {

        return repository.findAll()
                .stream()
                .map(ServiceResponse::from)
                .toList();
    }

    /**
     * Update a monitored service's health status.
     */
    @Transactional
    public ServiceResponse updateHealth(Long id, String healthStatus) {

        String normalized = healthStatus == null
                ? ""
                : healthStatus.trim().toUpperCase();

        if (!List.of("UP", "DEGRADED", "DOWN", "UNKNOWN")
                .contains(normalized)) {

            throw new IllegalArgumentException(
                    "healthStatus must be UP, DEGRADED, DOWN, or UNKNOWN"
            );
        }

        MonitoredService service = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Monitored service not found with id " + id
                ));

        service.setHealthStatus(normalized);

        MonitoredService updatedService = repository.save(service);

        return ServiceResponse.from(updatedService);
    }
}