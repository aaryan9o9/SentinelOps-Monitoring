package com.sentinelops.dto;

import com.sentinelops.entity.IncidentStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateIncidentStatusRequest(

        @NotNull(message = "Incident status is required")
        IncidentStatus status

) {}