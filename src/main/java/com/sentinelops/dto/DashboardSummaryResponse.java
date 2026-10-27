package com.sentinelops.dto;

public record DashboardSummaryResponse(
        long totalIncidents,
        long openIncidents,
        long investigatingIncidents,
        long resolvedIncidents,
        long highSeverityIncidents,
        long criticalSeverityIncidents,
        long monitoredServices
) {
}