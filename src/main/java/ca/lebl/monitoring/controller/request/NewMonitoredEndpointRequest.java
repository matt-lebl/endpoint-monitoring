package ca.lebl.monitoring.controller.request;

public class NewMonitoredEndpointRequest {

    private String url;
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
