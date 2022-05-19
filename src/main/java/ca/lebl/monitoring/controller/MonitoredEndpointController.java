package ca.lebl.monitoring.controller;

import ca.lebl.monitoring.entity.MonitoredEndpoint;
import ca.lebl.monitoring.entity.User;
import ca.lebl.monitoring.service.MonitoredEndpointService;
import ca.lebl.monitoring.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("endpoints")
public class MonitoredEndpointController {

    private final MonitoredEndpointService endpointService;
    private final UserService userService;

    public MonitoredEndpointController(
        MonitoredEndpointService endpointService,
        UserService userService
    ) {
        this.endpointService = endpointService;
        this.userService = userService;
    }

    @GetMapping
    public List<MonitoredEndpoint> getMonitoredEndpointsForUser(
        @RequestParam("accessToken") String accessToken
    ) {
        User user = userService.getUserByAccessToken(accessToken);

        return endpointService.listByOwner(user);
    }

    @PostMapping
    public MonitoredEndpoint postMonitoredEndpoint(
        @RequestParam("accessToken") String accessToken,
        @RequestParam("url") String url,
        @RequestParam("interval") Integer interval
    ) {
        User user = userService.getUserByAccessToken(accessToken);

        return endpointService.createMonitoredEndpoint(user, url, interval);
    }

}
