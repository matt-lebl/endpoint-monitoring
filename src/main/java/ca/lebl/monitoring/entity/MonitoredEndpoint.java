package ca.lebl.monitoring.entity;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Calendar;

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

    @ManyToOne
    private User owner;

    @Column
    private Integer interval;

    public MonitoredEndpoint(User owner, String url, Integer interval) {
        this.owner = owner;
        this.url = url;
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
}
