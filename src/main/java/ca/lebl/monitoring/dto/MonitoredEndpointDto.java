package ca.lebl.monitoring.dto;

import java.time.ZonedDateTime;

public class MonitoredEndpointDto {

    private Long id;
    private String url;
    private ZonedDateTime lastChecked;
    private ZonedDateTime created;

    public MonitoredEndpointDto(Long id, String url, ZonedDateTime lastChecked, ZonedDateTime created) {
        this.id = id;
        this.url = url;
        this.lastChecked = lastChecked;
        this.created = created;
    }

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

    public ZonedDateTime getLastChecked() {
        return lastChecked;
    }

    public void setLastChecked(ZonedDateTime lastChecked) {
        this.lastChecked = lastChecked;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }
}
