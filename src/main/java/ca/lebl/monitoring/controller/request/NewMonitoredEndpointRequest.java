package ca.lebl.monitoring.controller.request;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Positive;

public class NewMonitoredEndpointRequest {

    @URL
    private String url;
    @Positive
    private Integer interval;

    public NewMonitoredEndpointRequest(String url, Integer interval) {
        this.url = url;
        this.interval = interval;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }
}
