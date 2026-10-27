package com.sentinelops.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "incidents", indexes = {
    @Index(name = "idx_incident_created_at", columnList = "createdAt"),
    @Index(name = "idx_incident_status", columnList = "status"),
    @Index(name = "idx_incident_severity", columnList = "severity")
})
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 160)
    private String title;

    @Column(nullable = false, length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private IncidentSeverity severity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private IncidentStatus status = IncidentStatus.OPEN;

    @Column(length = 120)
    private String source;

    @Column(length = 3000)
    private String evidence;

    @Column(length = 3000)
    private String aiAnalysis;

    @Column(length = 3000)
    private String recommendation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    private MonitoredService monitoredService;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    private Instant updatedAt = Instant.now();

    protected Incident() {}

    public Incident(String title, String description, IncidentSeverity severity,
                    String source, String evidence, MonitoredService monitoredService) {
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.source = source;
        this.evidence = evidence;
        this.monitoredService = monitoredService;
        this.status = IncidentStatus.OPEN;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public IncidentSeverity getSeverity() { return severity; }
    public IncidentStatus getStatus() { return status; }
    public String getSource() { return source; }
    public String getEvidence() { return evidence; }
    public String getAiAnalysis() { return aiAnalysis; }
    public String getRecommendation() { return recommendation; }
    public MonitoredService getMonitoredService() { return monitoredService; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void setStatus(IncidentStatus status) { this.status = status; }
    public void setAiAnalysis(String aiAnalysis) { this.aiAnalysis = aiAnalysis; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }
}
