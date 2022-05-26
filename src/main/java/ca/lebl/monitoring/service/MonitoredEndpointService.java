package ca.lebl.monitoring.service;

import ca.lebl.monitoring.entity.MonitoredEndpoint;
import ca.lebl.monitoring.entity.User;
import ca.lebl.monitoring.exception.EndpointNotFoundException;
import ca.lebl.monitoring.repository.MonitoredEndpointRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@EnableScheduling
public class MonitoredEndpointService {

    private final MonitoredEndpointRepository endpointRepository;
    private final MonitoringResultService resultService;

    public MonitoredEndpointService(MonitoredEndpointRepository endpointRepository, MonitoringResultService resultService) {
        this.endpointRepository = endpointRepository;
        this.resultService = resultService;
    }

    public List<MonitoredEndpoint> listByOwner(User user) {
        return endpointRepository.findByOwner(user);
    }

    public MonitoredEndpoint createMonitoredEndpoint(User user, String url, Integer interval) {
        MonitoredEndpoint endpoint = new MonitoredEndpoint(user, url, ZonedDateTime.now(), interval);

        endpointRepository.save(endpoint);

        return endpoint;
    }

    public MonitoredEndpoint getEndpointById(Long endpointId) {
        User loggedInUser = getUser();
        MonitoredEndpoint endpoint = endpointRepository.findByIdAndOwner(endpointId, loggedInUser).orElseThrow(EndpointNotFoundException::new);

        return endpoint;
    }

    public List<MonitoredEndpoint> getNextEndpointsToCheck() {
        return endpointRepository.findByNextCheckBefore(ZonedDateTime.now());
    }

    void rescheduleEndpointCheck(MonitoredEndpoint endpoint) {
        ZonedDateTime lastCheck = ZonedDateTime.now();
        endpoint.setLastChecked(lastCheck);
        endpoint.setNextCheck(lastCheck.plus(endpoint.getInterval(), ChronoUnit.SECONDS));
        endpointRepository.save(endpoint);
    }

    @Scheduled(fixedRate = 5L, timeUnit = TimeUnit.SECONDS)
    void monitorEndpoints() { // <-- where the magic happens !!
        List<MonitoredEndpoint> endpointsToCheck = getNextEndpointsToCheck();

        for (MonitoredEndpoint endpoint : endpointsToCheck) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(endpoint.getUrl(), String.class);

            resultService.recordResultForEndpoint(
                endpoint,
                ZonedDateTime.now(),
                response.getStatusCodeValue(),
                response.getBody()
            );
        }
    }

    private User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
