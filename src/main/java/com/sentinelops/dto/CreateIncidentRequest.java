package com.sentinelops.dto;

import com.sentinelops.entity.IncidentSeverity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateIncidentRequest(

        @NotBlank(message = "Incident title is required")
        @Size(max = 160, message = "Title cannot exceed 160 characters")
        String title,

        @NotBlank(message = "Incident description is required")
        @Size(max = 2000, message = "Description cannot exceed 2000 characters")
        String description,

        @NotNull(message = "Incident severity is required")
        IncidentSeverity severity,

        @Size(max = 120, message = "Source cannot exceed 120 characters")
        String source,

        @Size(max = 3000, message = "Evidence cannot exceed 3000 characters")
        String evidence,

        Long serviceId

) {}