package ca.lebl.monitoring.dto;

import ca.lebl.monitoring.entity.MonitoredEndpoint;
import ca.lebl.monitoring.entity.MonitoringResult;
import ca.lebl.monitoring.entity.User;
import org.springframework.stereotype.Component;

public class DtoMapper {

    public static MonitoredEndpointDto toDto(MonitoredEndpoint entity) {
        return new MonitoredEndpointDto(
            entity.getId(),
            entity.getUrl(),
            entity.getLastChecked(),
            entity.getCreated()
        );
    }

    public static MonitoringResultDto toDto(MonitoringResult entity) {
        return new MonitoringResultDto(
            entity.getId(),
            entity.getDateOfCheck(),
            entity.getHttpStatusCode(),
            entity.getPayload(),
            entity.getEndpoint().getId()
        );
    }

}
