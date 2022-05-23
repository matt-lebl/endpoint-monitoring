package ca.lebl.monitoring.repository;

import ca.lebl.monitoring.entity.MonitoredEndpoint;
import ca.lebl.monitoring.entity.MonitoringResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface MonitoringResultRepository extends JpaRepository<MonitoringResult, Long> {

    Stream<MonitoringResult> findByEndpointOrderByDateOfCheck(MonitoredEndpoint endpoint);

}
