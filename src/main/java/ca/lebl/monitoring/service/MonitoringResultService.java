package ca.lebl.monitoring.service;

import ca.lebl.monitoring.entity.MonitoredEndpoint;
import ca.lebl.monitoring.entity.MonitoringResult;
import ca.lebl.monitoring.repository.MonitoringResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoringResultService {

    private final MonitoringResultRepository resultRepository;

    public MonitoringResultService(MonitoringResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public List<MonitoringResult> getTenLatestResultsForEndpoint(MonitoredEndpoint endpoint) {

        return resultRepository.findByMonitoredEndpointOrderByDateOfCheck(endpoint).limit(10).toList();

    }

}
