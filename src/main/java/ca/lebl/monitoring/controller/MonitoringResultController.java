package ca.lebl.monitoring.controller;

import ca.lebl.monitoring.dto.MonitoringResultDto;
import ca.lebl.monitoring.entity.MonitoredEndpoint;
import ca.lebl.monitoring.entity.MonitoringResult;
import ca.lebl.monitoring.service.MonitoredEndpointService;
import ca.lebl.monitoring.service.MonitoringResultService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

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
    @Transactional(readOnly = true)
    public List<MonitoringResultDto> listLatestTenMonitoringResultsByEndpoint(
        @RequestParam("id") Long endpointId
    ) {
        MonitoredEndpoint endpoint = endpointService.getEndpointById(endpointId);
        try (Stream<MonitoringResult> resultStream = resultService.getTenLatestResultsForEndpoint(endpoint)) {
            return resultStream.map(MonitoringResult::toDto).toList();
        }
    }

}
