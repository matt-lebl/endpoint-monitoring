package ca.lebl.monitoring.entity;

import ca.lebl.monitoring.dto.MonitoredEndpointDto;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "monitoring_monitoredendpoint")
public class MonitoredEndpoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String url;

    @Column
    private ZonedDateTime created;

    @Column
    private ZonedDateTime lastChecked;

    @Column
    private ZonedDateTime nextCheck;

    @ManyToOne
    private User owner;

    @Column
    private Integer interval;

    public MonitoredEndpoint(User owner, String url, ZonedDateTime nextCheck, Integer interval) {
        this.owner = owner;
        this.url = url;
        this.nextCheck = nextCheck;
        this.interval = interval;
        this.created = ZonedDateTime.now();
        this.lastChecked = null;
    }

    public MonitoredEndpoint() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getLastChecked() {
        return lastChecked;
    }

    public void setLastChecked(ZonedDateTime lastChecked) {
        this.lastChecked = lastChecked;
    }

    public ZonedDateTime getNextCheck() {
        return nextCheck;
    }

    public void setNextCheck(ZonedDateTime nextCheck) {
        this.nextCheck = nextCheck;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public MonitoredEndpointDto toDto() {
        return new MonitoredEndpointDto(
            id,
            url,
            lastChecked,
            created
        );
    }
}
