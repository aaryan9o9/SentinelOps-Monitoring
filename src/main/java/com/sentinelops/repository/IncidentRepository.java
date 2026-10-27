package com.sentinelops.repository;

import com.sentinelops.entity.Incident;
import com.sentinelops.entity.IncidentSeverity;
import com.sentinelops.entity.IncidentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
    List<Incident> findAllByOrderByCreatedAtDesc();
    List<Incident> findByStatusOrderByCreatedAtDesc(IncidentStatus status);
    List<Incident> findBySeverityOrderByCreatedAtDesc(IncidentSeverity severity);
    long countByStatus(IncidentStatus status);
    long countBySeverity(IncidentSeverity severity);

    List<Incident> findByStatusAndSeverityOrderByCreatedAtDesc(IncidentStatus status, IncidentSeverity severity);
}
