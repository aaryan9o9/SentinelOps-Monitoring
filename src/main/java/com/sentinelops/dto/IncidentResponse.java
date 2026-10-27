package com.sentinelops.dto;

import com.sentinelops.entity.Incident;
import com.sentinelops.entity.IncidentSeverity;
import com.sentinelops.entity.IncidentStatus;

import java.time.Instant;

public record IncidentResponse(
        Long id,
        String title,
        String description,
        IncidentSeverity severity,
        IncidentStatus status,
        String source,
        String evidence,
        String aiAnalysis,
        String recommendation,
        Long serviceId,
        String serviceName,
        Instant createdAt,
        Instant updatedAt
) {

    public static IncidentResponse from(Incident incident) {

        Long serviceId = null;
        String serviceName = null;

        if (incident.getMonitoredService() != null) {
            serviceId = incident.getMonitoredService().getId();
            serviceName = incident.getMonitoredService().getName();
        }

        return new IncidentResponse(
                incident.getId(),
                incident.getTitle(),
                incident.getDescription(),
                incident.getSeverity(),
                incident.getStatus(),
                incident.getSource(),
                incident.getEvidence(),
                incident.getAiAnalysis(),
                incident.getRecommendation(),
                serviceId,
                serviceName,
                incident.getCreatedAt(),
                incident.getUpdatedAt()
        );
    }
}