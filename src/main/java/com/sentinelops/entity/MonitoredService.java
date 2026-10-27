package com.sentinelops.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "monitored_services")
public class MonitoredService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 120)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, length = 30)
    private String healthStatus = "UNKNOWN";

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    protected MonitoredService() {}

    public MonitoredService(String name, String description) {
        this.name = name;
        this.description = description;
        this.healthStatus = "UNKNOWN";
        this.createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getHealthStatus() { return healthStatus; }
    public Instant getCreatedAt() { return createdAt; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setHealthStatus(String healthStatus) { this.healthStatus = healthStatus; }
}
