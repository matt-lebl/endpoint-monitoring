package ca.lebl.monitoring.controller;

import ca.lebl.monitoring.entity.MonitoredEndpoint;
import ca.lebl.monitoring.entity.MonitoringResult;
import ca.lebl.monitoring.entity.User;
import ca.lebl.monitoring.service.MonitoredEndpointService;
import ca.lebl.monitoring.service.MonitoringResultService;
import ca.lebl.monitoring.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("results")
public class MonitoringResultController {

    private final MonitoringResultService resultService;
    private final UserService userService;
    private final MonitoredEndpointService endpointService;

    public MonitoringResultController(MonitoringResultService resultService, UserService userService, MonitoredEndpointService endpointService) {
        this.resultService = resultService;
        this.userService = userService;
        this.endpointService = endpointService;
    }

    @GetMapping
    public List<MonitoringResult> listLatestTenMonitoringResultsByEndpoint(
        @RequestParam("accessToken") String accessToken,
        @RequestParam("id") Long endpointId
    ) {
        User user = userService.getUserByAccessToken(accessToken);
        List<MonitoredEndpoint> endpoints = endpointService.listByOwner(user);

        for (MonitoredEndpoint endpoint : endpoints) {

        }

        resultService.getTenLatestResultsForEndpoint()
    }

}
