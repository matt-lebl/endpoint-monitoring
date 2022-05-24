package ca.lebl.monitoring.controller;

import ca.lebl.monitoring.dto.MonitoredEndpointDto;
import ca.lebl.monitoring.entity.MonitoredEndpoint;
import ca.lebl.monitoring.entity.User;
import ca.lebl.monitoring.service.MonitoredEndpointService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("endpoints")
public class MonitoredEndpointController {

    private final MonitoredEndpointService endpointService;

    public MonitoredEndpointController(
        MonitoredEndpointService endpointService
    ) {
        this.endpointService = endpointService;
    }

    @GetMapping
    public List<MonitoredEndpointDto> getMonitoredEndpointsForUser() {
        User user = getUser();

        return endpointService.listByOwner(user).stream().map(MonitoredEndpoint::toDto).toList();
    }

    @PostMapping
    public MonitoredEndpointDto postMonitoredEndpoint(
        @RequestParam("url") String url,
        @RequestParam("interval") Integer interval
    ) {
        User user = getUser();

        return endpointService.createMonitoredEndpoint(user, url, interval).toDto();
    }

    private User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
