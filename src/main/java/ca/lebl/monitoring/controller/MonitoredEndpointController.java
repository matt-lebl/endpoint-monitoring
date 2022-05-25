package ca.lebl.monitoring.controller;

import ca.lebl.monitoring.controller.request.NewMonitoredEndpointRequest;
import ca.lebl.monitoring.dto.MonitoredEndpointDto;
import ca.lebl.monitoring.entity.MonitoredEndpoint;
import ca.lebl.monitoring.entity.User;
import ca.lebl.monitoring.service.MonitoredEndpointService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("endpoints")
@Validated
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
        @RequestBody @Valid NewMonitoredEndpointRequest request
    ) {
        User user = getUser();

        return endpointService.createMonitoredEndpoint(user, request.getUrl(), request.getInterval()).toDto();
    }

    private User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
