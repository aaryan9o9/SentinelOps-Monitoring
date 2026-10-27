package com.sentinelops.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateServiceRequest(

        @NotBlank(message = "Service name is required")
        @Size(max = 120, message = "Service name cannot exceed 120 characters")
        String name,

        @Size(max = 500, message = "Service description cannot exceed 500 characters")
        String description

) {}