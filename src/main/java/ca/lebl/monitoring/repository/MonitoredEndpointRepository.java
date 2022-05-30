package ca.lebl.monitoring.repository;

import ca.lebl.monitoring.entity.MonitoredEndpoint;
import ca.lebl.monitoring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MonitoredEndpointRepository extends JpaRepository<MonitoredEndpoint, Long> {

    List<MonitoredEndpoint> findByOwner(User user);

    Optional<MonitoredEndpoint> findById(Long id);

    Optional<MonitoredEndpoint> findByIdAndOwner(Long id, User owner);

    List<MonitoredEndpoint> findByNextCheckBefore(ZonedDateTime time);

}
