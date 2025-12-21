package com.trallkong.psmsbackend.controller;

import com.trallkong.psmsbackend.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/unpaidSum")
    public String unpaidSum() {
        return dashboardService.unpaidSum();
    }

    @GetMapping("/paidSum")
    public String paidSum() {
        return dashboardService.paidSum();
    }

    @GetMapping("/overdueCount")
    public String overdueCount() {
        return dashboardService.overdueCount();
    }

    @GetMapping("/ownerCount")
    public String ownerCount() {
        return dashboardService.ownerCount();
    }
}
