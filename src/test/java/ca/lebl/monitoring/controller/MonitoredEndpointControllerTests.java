package ca.lebl.monitoring.controller;

import ca.lebl.monitoring.controller.request.NewMonitoredEndpointRequest;
import ca.lebl.monitoring.entity.MonitoredEndpoint;
import ca.lebl.monitoring.service.MonitoredEndpointService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MonitoredEndpointController.class)
public class MonitoredEndpointControllerTests extends ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private JacksonTester<NewMonitoredEndpointRequest> newMonitoredEndpoint;

    @BeforeEach
    void setupJacksonTester() {
        // i honestly don't know what magic this is but i found it at
        // https://thepracticaldeveloper.com/guide-spring-boot-controller-tests/#strategy-1-spring-mockmvc-example-in-standalone-mode
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @BeforeEach
    void setupDummyMonitoredEndpoint() {
        // createMonitoredEndpoint can return a dummy since we're not
        // verifying the correctness of the data, just that the method
        // was called.
        when(endpointService.createMonitoredEndpoint(any(), any(), any()))
            .thenReturn(mock(MonitoredEndpoint.class));
    }

    @MockBean
    private MonitoredEndpointService endpointService;

    @Test
    void testAuthorized() throws Exception {
        MockHttpServletRequestBuilder request = get("/endpoints");
        addAuthorizedToken(request);

        mockMvc.perform(request)
            .andExpect(status().isOk());
    }

    @Test
    void testUnauthorized() throws Exception {
        MockHttpServletRequestBuilder request = get("/endpoints");
        addUnauthorizedToken(request);

        mockMvc.perform(request)
            .andExpect(status().isUnauthorized());
    }

    @Test
    void testNoAuthToken() throws Exception {
        MockHttpServletRequestBuilder request = get("/endpoints");

        mockMvc.perform(request)
            .andExpect(status().isUnauthorized());
    }

    @Test
    void testCreateMonitoredEndpoint() throws Exception {
        String url = "http://www.google.com/";
        Integer interval = 60;

        MockHttpServletRequestBuilder request = post("/endpoints")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newMonitoredEndpoint.write(new NewMonitoredEndpointRequest(url, interval)).getJson());

        addAuthorizedToken(request);

        mockMvc.perform(request)
            .andExpect(status().isOk());

        verify(endpointService, times(1))
            .createMonitoredEndpoint(AUTHORIZED_USER, url, interval);
    }

    @Test
    void testCreateInvalidURL() throws Exception {
        String url = "badurl";
        Integer interval = 50;

        MockHttpServletRequestBuilder request = post("/endpoints")
            .contentType(MediaType.APPLICATION_JSON)
            .content(newMonitoredEndpoint.write(new NewMonitoredEndpointRequest(url, interval)).getJson());

        addAuthorizedToken(request);

        mockMvc.perform(request)
            .andExpect(status().isBadRequest());
    }

}
