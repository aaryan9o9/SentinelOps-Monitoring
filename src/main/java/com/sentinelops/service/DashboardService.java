package com.sentinelops.service;

import com.sentinelops.dto.DashboardSummaryResponse;
import com.sentinelops.entity.IncidentSeverity;
import com.sentinelops.entity.IncidentStatus;
import com.sentinelops.repository.IncidentRepository;
import com.sentinelops.repository.MonitoredServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardService {

    private final IncidentRepository incidentRepository;
    private final MonitoredServiceRepository serviceRepository;

    public DashboardService(
            IncidentRepository incidentRepository,
            MonitoredServiceRepository serviceRepository
    ) {
        this.incidentRepository = incidentRepository;
        this.serviceRepository = serviceRepository;
    }

    @Transactional(readOnly = true)
    public DashboardSummaryResponse getSummary() {

        long totalIncidents = incidentRepository.count();

        long openIncidents =
                incidentRepository.countByStatus(IncidentStatus.OPEN);

        long investigatingIncidents =
                incidentRepository.countByStatus(IncidentStatus.INVESTIGATING);

        long resolvedIncidents =
                incidentRepository.countByStatus(IncidentStatus.RESOLVED);

        long highSeverityIncidents =
                incidentRepository.countBySeverity(IncidentSeverity.HIGH);

        long criticalSeverityIncidents =
                incidentRepository.countBySeverity(IncidentSeverity.CRITICAL);

        long monitoredServices = serviceRepository.count();

        return new DashboardSummaryResponse(
                totalIncidents,
                openIncidents,
                investigatingIncidents,
                resolvedIncidents,
                highSeverityIncidents,
                criticalSeverityIncidents,
                monitoredServices
        );
    }
}