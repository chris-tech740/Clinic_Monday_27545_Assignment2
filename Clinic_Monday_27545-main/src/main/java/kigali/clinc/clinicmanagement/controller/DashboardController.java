package kigali.clinc.clinicmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kigali.clinc.clinicmanagement.service.DashboardService;

@RestController
@RequestMapping(value = "/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    // Quiz 2 - D4
    @GetMapping(value = "/summary")
    public ResponseEntity<?> getSummary() {

        return new ResponseEntity<>(
                dashboardService.getSummary(),
                HttpStatus.OK);
    }
}