package com.sentinelops.service;

import com.sentinelops.dto.CreateIncidentRequest;
import com.sentinelops.dto.IncidentResponse;
import com.sentinelops.dto.UpdateIncidentStatusRequest;
import com.sentinelops.entity.Incident;
import com.sentinelops.entity.IncidentSeverity;
import com.sentinelops.entity.IncidentStatus;
import com.sentinelops.entity.MonitoredService;
import com.sentinelops.exception.ResourceNotFoundException;
import com.sentinelops.repository.IncidentRepository;
import com.sentinelops.repository.MonitoredServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final MonitoredServiceRepository serviceRepository;

    public IncidentService(
            IncidentRepository incidentRepository,
            MonitoredServiceRepository serviceRepository
    ) {
        this.incidentRepository = incidentRepository;
        this.serviceRepository = serviceRepository;
    }

    /**
     * Create a new incident.
     */
    @Transactional
    public IncidentResponse create(CreateIncidentRequest request) {

        MonitoredService monitoredService = null;

        if (request.serviceId() != null) {
            if (request.serviceId() <= 0) {
                throw new IllegalArgumentException(
                        "Monitored service ID must be positive"
                );
            }

            monitoredService = serviceRepository.findById(request.serviceId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Monitored service not found with id "
                                    + request.serviceId()
                    ));
        }

        Incident incident = new Incident(
                request.title(),
                request.description(),
                request.severity(),
                request.source(),
                request.evidence(),
                monitoredService
        );

        Incident savedIncident = incidentRepository.save(incident);

        return IncidentResponse.from(savedIncident);
    }

    /**
     * Retrieve incidents with optional status and severity filters.
     */
    @Transactional(readOnly = true)
    public List<IncidentResponse> getAll(
            IncidentStatus status,
            IncidentSeverity severity
    ) {

        List<Incident> incidents;

        if (status != null && severity != null) {

            incidents =
                    incidentRepository
                            .findByStatusAndSeverityOrderByCreatedAtDesc(
                                    status,
                                    severity
                            );

        } else if (status != null) {

            incidents =
                    incidentRepository
                            .findByStatusOrderByCreatedAtDesc(status);

        } else if (severity != null) {

            incidents =
                    incidentRepository
                            .findBySeverityOrderByCreatedAtDesc(severity);

        } else {

            incidents =
                    incidentRepository
                            .findAllByOrderByCreatedAtDesc();
        }

        return incidents.stream()
                .map(IncidentResponse::from)
                .toList();
    }

    /**
     * Retrieve an incident by ID.
     */
    @Transactional(readOnly = true)
    public IncidentResponse getById(Long id) {

        return IncidentResponse.from(findIncident(id));
    }

    /**
     * Update an incident's status.
     */
    @Transactional
    public IncidentResponse updateStatus(
            Long id,
            UpdateIncidentStatusRequest request
    ) {

        Incident incident = findIncident(id);

        incident.setStatus(request.status());

        Incident updatedIncident = incidentRepository.save(incident);

        return IncidentResponse.from(updatedIncident);
    }

    /**
     * Save AI-generated analysis and recommendations.
     */
    @Transactional
    public IncidentResponse addAiAnalysis(
            Long id,
            String analysis,
            String recommendation
    ) {

        if (analysis == null || analysis.isBlank()) {
            throw new IllegalArgumentException(
                    "AI analysis cannot be empty"
            );
        }

        if (analysis.length() > 3000) {
            throw new IllegalArgumentException(
                    "AI analysis cannot exceed 3000 characters"
            );
        }

        if (recommendation != null && recommendation.length() > 3000) {
            throw new IllegalArgumentException(
                    "Recommendation cannot exceed 3000 characters"
            );
        }

        Incident incident = findIncident(id);

        incident.setAiAnalysis(analysis);
        incident.setRecommendation(recommendation);

        Incident updatedIncident = incidentRepository.save(incident);

        return IncidentResponse.from(updatedIncident);
    }

    /**
     * Find an incident or throw an exception if it doesn't exist.
     */
    private Incident findIncident(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Incident ID must be a positive number"
            );
        }

        return incidentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Incident not found with id " + id
                ));
    }
}