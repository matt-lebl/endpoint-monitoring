package ca.lebl.monitoring.entity;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Calendar;

@Entity
@Table(name = "monitoring_monitoringresult")
public class MonitoringResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private ZonedDateTime dateOfCheck;

    @Column
    private String payload;

    @Column
    private Integer httpStatusCode;

    @ManyToOne
    private MonitoredEndpoint endpoint;

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

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public MonitoredEndpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(MonitoredEndpoint endpoint) {
        this.endpoint = endpoint;
    }
}
