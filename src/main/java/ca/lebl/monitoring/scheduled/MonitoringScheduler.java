package ca.lebl.monitoring.scheduled;

import ca.lebl.monitoring.entity.MonitoredEndpoint;
import ca.lebl.monitoring.service.MonitoringResultService;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class MonitoringScheduler {

    private MonitoringResultService resultService;
    private TaskScheduler scheduler;

    public MonitoringScheduler(MonitoringResultService resultService, TaskScheduler scheduler) {
        this.resultService = resultService;
        this.scheduler = scheduler;
    }

    public void beginMonitoringEndpoint(MonitoredEndpoint endpoint) {
        MonitoringTask task = new MonitoringTask(endpoint, resultService);
        scheduler.scheduleAtFixedRate(task, Duration.ofSeconds(endpoint.getInterval()));
    }

}
