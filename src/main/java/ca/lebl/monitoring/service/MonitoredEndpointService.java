package ca.lebl.monitoring.service;

import ca.lebl.monitoring.entity.MonitoredEndpoint;
import ca.lebl.monitoring.entity.User;
import ca.lebl.monitoring.exception.EndpointNotFoundException;
import ca.lebl.monitoring.repository.MonitoredEndpointRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
            HttpURLConnection conn;

            try {
                URL url = new URL(endpoint.getUrl());
                conn = (HttpURLConnection) url.openConnection();
            } catch (Exception e) {
                // what goes here?
                return;
            }

            try {
                Integer httpStatusCode = conn.getResponseCode();
                Reader streamReader;

                if (httpStatusCode > 299) {
                    streamReader = new InputStreamReader(conn.getErrorStream());
                } else {
                    streamReader = new InputStreamReader(conn.getInputStream());
                }

                String payload = new BufferedReader(streamReader)
                    .lines().collect(Collectors.joining("\n"));

                resultService.recordResultForEndpoint(
                    endpoint,
                    ZonedDateTime.now(),
                    httpStatusCode,
                    payload
                );

                rescheduleEndpointCheck(endpoint);

            } catch (IOException e) {
                // what goes here?
                return;
            }
        }
    }

    private User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
