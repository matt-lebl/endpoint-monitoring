package ca.lebl.monitoring.controller;

import ca.lebl.monitoring.entity.MonitoredEndpoint;
import ca.lebl.monitoring.entity.MonitoringResult;
import ca.lebl.monitoring.service.MonitoredEndpointService;
import ca.lebl.monitoring.service.MonitoringResultService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("results")
public class MonitoringResultController {

    private final MonitoringResultService resultService;
    private final MonitoredEndpointService endpointService;

    public MonitoringResultController(MonitoringResultService resultService, MonitoredEndpointService endpointService) {
        this.resultService = resultService;
        this.endpointService = endpointService;
    }

    @GetMapping
    public List<MonitoringResult> listLatestTenMonitoringResultsByEndpoint(
        @RequestParam("id") Long endpointId
    ) {
        MonitoredEndpoint endpoint = endpointService.getEndpointById(endpointId);
        return resultService.getTenLatestResultsForEndpoint(endpoint);
    }

}
