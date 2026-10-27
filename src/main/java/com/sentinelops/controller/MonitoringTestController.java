
package com.sentinelops.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class MonitoringTestController {

    @GetMapping("/test/500")
    public String generateTestError() {
        throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "SentinelOps monitoring test"
        );
    }
}
