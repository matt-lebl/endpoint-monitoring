package ca.lebl.monitoring.service;

import ca.lebl.monitoring.entity.MonitoredEndpoint;
import ca.lebl.monitoring.entity.User;
import ca.lebl.monitoring.exception.EndpointNotFoundException;
import ca.lebl.monitoring.repository.MonitoredEndpointRepository;
import ca.lebl.monitoring.scheduled.MonitoringScheduler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoredEndpointService {

    private final MonitoredEndpointRepository endpointRepository;
    private final MonitoringScheduler scheduler;

    public MonitoredEndpointService(MonitoredEndpointRepository endpointRepository, MonitoringScheduler scheduler) {
        this.endpointRepository = endpointRepository;
        this.scheduler = scheduler;
    }

    public List<MonitoredEndpoint> listByOwner(User user) {
        return endpointRepository.findByOwner(user);
    }

    public MonitoredEndpoint createMonitoredEndpoint(User user, String url, Integer interval) {
        MonitoredEndpoint endpoint = new MonitoredEndpoint(user, url, interval);

        endpointRepository.save(endpoint);

        scheduler.beginMonitoringEndpoint(endpoint);

        return endpoint;
    }

    public MonitoredEndpoint getEndpointById(Long endpointId) {
        User loggedInUser = getUser();
        MonitoredEndpoint endpoint = endpointRepository.findByIdAndOwner(endpointId, loggedInUser).orElseThrow(EndpointNotFoundException::new);

        return endpoint;
    }

    private User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
