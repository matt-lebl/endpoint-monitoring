package ca.lebl.monitoring.entity;

import javax.persistence.*;
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
    private Calendar created;

    @Column
    private Calendar lastChecked;

    @ManyToOne
    private User owner;

    @Column
    private Integer interval;

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

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public Calendar getLastChecked() {
        return lastChecked;
    }

    public void setLastChecked(Calendar lastChecked) {
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
