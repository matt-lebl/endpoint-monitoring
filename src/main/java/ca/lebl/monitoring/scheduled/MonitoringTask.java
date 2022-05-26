package ca.lebl.monitoring.scheduled;

import ca.lebl.monitoring.entity.MonitoredEndpoint;
import ca.lebl.monitoring.service.MonitoringResultService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;

public class MonitoringTask implements Runnable {

    private MonitoredEndpoint endpoint;
    private MonitoringResultService resultService;

    public MonitoringTask(MonitoredEndpoint endpoint, MonitoringResultService resultService) {
        this.endpoint = endpoint;
        this.resultService = resultService;
    }

    @Override
    public void run() {
        HttpURLConnection conn;

        try {
            URL url = new URL(endpoint.getUrl());
            conn = (HttpURLConnection) url.openConnection();
        } catch (Exception e) {
            // what goes here?
            return;
        }

        try {
            Integer httpStatusCode = conn.getResponseCode();
            Reader streamReader;

            if (httpStatusCode > 299) {
                streamReader = new InputStreamReader(conn.getErrorStream());
            } else {
                streamReader = new InputStreamReader(conn.getInputStream());
            }

            String payload = new BufferedReader(streamReader)
                .lines().collect(Collectors.joining("\n"));

            resultService.recordResultForEndpoint(
                endpoint,
                ZonedDateTime.now(),
                httpStatusCode,
                payload
            );

        } catch (IOException e) {
            // what goes here?
            return;
        }
    }

}
