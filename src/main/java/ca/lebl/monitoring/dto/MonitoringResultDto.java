package ca.lebl.monitoring.dto;

import java.time.ZonedDateTime;

public class MonitoringResultDto {

    private Long id;
    private ZonedDateTime dateOfCheck;
    private Integer httpStatusCode;
    private String payload;
    private Long monitoredEndpointId;

    public MonitoringResultDto(Long id, ZonedDateTime dateOfCheck, Integer httpStatusCode, String payload, Long monitoredEndpointId) {
        this.id = id;
        this.dateOfCheck = dateOfCheck;
        this.httpStatusCode = httpStatusCode;
        this.payload = payload;
        this.monitoredEndpointId = monitoredEndpointId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateOfCheck() {
        return dateOfCheck;
    }

    public void setDateOfCheck(ZonedDateTime dateOfCheck) {
        this.dateOfCheck = dateOfCheck;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Long getMonitoredEndpointId() {
        return monitoredEndpointId;
    }

    public void setMonitoredEndpointId(Long monitoredEndpointId) {
        this.monitoredEndpointId = monitoredEndpointId;
    }

}
