package ca.lebl.monitoring.service;

import ca.lebl.monitoring.entity.MonitoredEndpoint;
import ca.lebl.monitoring.entity.User;
import ca.lebl.monitoring.repository.MonitoredEndpointRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoredEndpointService {

    private final MonitoredEndpointRepository endpointRepository;

    public MonitoredEndpointService(MonitoredEndpointRepository endpointRepository) {
        this.endpointRepository = endpointRepository;
    }

    public List<MonitoredEndpoint> listByOwner(User user) {
        return endpointRepository.findByOwner(user);
    }
}
