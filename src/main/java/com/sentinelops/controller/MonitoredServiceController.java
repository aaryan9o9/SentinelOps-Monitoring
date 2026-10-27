package com.sentinelops.controller;

import com.sentinelops.dto.CreateServiceRequest;
import com.sentinelops.dto.ServiceResponse;
import com.sentinelops.service.MonitoredServiceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class MonitoredServiceController {

    private final MonitoredServiceService service;

    public MonitoredServiceController(MonitoredServiceService service) {
        this.service = service;
    }

    /**
     * Create a monitored service.
     * POST /api/services
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse create(
            @Valid @RequestBody CreateServiceRequest request
    ) {
        return service.create(request);
    }

    /**
     * Retrieve all monitored services.
     * GET /api/services
     */
    @GetMapping
    public List<ServiceResponse> getAll() {
        return service.getAll();
    }

    /**
     * Update a monitored service's health status.
     * PATCH /api/services/{id}/health
     */
    @PatchMapping("/{id}/health")
    public ServiceResponse updateHealth(
            @PathVariable Long id,
            @Valid @RequestBody UpdateServiceHealthRequest request
    ) {
        return service.updateHealth(id, request.healthStatus());
    }

    /**
     * Validates health-status update requests.
     */
    public record UpdateServiceHealthRequest(

            @NotBlank(message = "Health status is required")
            @Pattern(
                    regexp = "(?i)UP|DEGRADED|DOWN|UNKNOWN",
                    message = "Health status must be UP, DEGRADED, DOWN, or UNKNOWN"
            )
            String healthStatus

    ) {
    }
}