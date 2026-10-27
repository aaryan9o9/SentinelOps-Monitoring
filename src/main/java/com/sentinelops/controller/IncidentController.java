package com.sentinelops.controller;

import com.sentinelops.dto.CreateIncidentRequest;
import com.sentinelops.dto.IncidentResponse;
import com.sentinelops.dto.UpdateIncidentStatusRequest;
import com.sentinelops.entity.IncidentSeverity;
import com.sentinelops.entity.IncidentStatus;
import com.sentinelops.service.IncidentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    /**
     * Create a new incident.
     * POST /api/incidents
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IncidentResponse create(
            @Valid @RequestBody CreateIncidentRequest request
    ) {
        return incidentService.create(request);
    }

    /**
     * Retrieve incidents, optionally filtered by status and severity.
     * GET /api/incidents
     * GET /api/incidents?status=OPEN
     * GET /api/incidents?severity=CRITICAL
     * GET /api/incidents?status=OPEN&severity=CRITICAL
     */
    @GetMapping
    public List<IncidentResponse> getAll(
            @RequestParam(required = false) IncidentStatus status,
            @RequestParam(required = false) IncidentSeverity severity
    ) {
        return incidentService.getAll(status, severity);
    }

    /**
     * Retrieve a single incident by ID.
     * GET /api/incidents/{id}
     */
    @GetMapping("/{id}")
    public IncidentResponse getById(@PathVariable Long id) {
        return incidentService.getById(id);
    }

    /**
     * Update an incident's status.
     * PATCH /api/incidents/{id}/status
     */
    @PatchMapping("/{id}/status")
    public IncidentResponse updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateIncidentStatusRequest request
    ) {
        return incidentService.updateStatus(id, request);
    }
}