package ca.lebl.monitoring.service;

import ca.lebl.monitoring.entity.MonitoredEndpoint;
import ca.lebl.monitoring.entity.MonitoringResult;
import ca.lebl.monitoring.repository.MonitoringResultRepository;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.stream.Stream;

@Service
public class MonitoringResultService {

    private final MonitoringResultRepository resultRepository;

    public MonitoringResultService(MonitoringResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public Stream<MonitoringResult> getTenLatestResultsForEndpoint(MonitoredEndpoint endpoint) {

        return resultRepository.findByEndpointOrderByDateOfCheck(endpoint).limit(10);

    }

    public void recordResultForEndpoint(MonitoredEndpoint endpoint, ZonedDateTime dateOfCheck, Integer httpStatusCode, String payload) {
        MonitoringResult result = new MonitoringResult(
            dateOfCheck,
            payload,
            httpStatusCode,
            endpoint
        );

        resultRepository.save(result);
    }

}
