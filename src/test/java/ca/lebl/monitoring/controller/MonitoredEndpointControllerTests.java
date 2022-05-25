package ca.lebl.monitoring.controller;

import ca.lebl.monitoring.entity.MonitoredEndpoint;
import ca.lebl.monitoring.service.MonitoredEndpointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
            .param("url", url)
            .param("interval", interval.toString());

        // createMonitoredEndpoint can return a dummy since we're not
        // verifying the correctness of the data, just that the method
        // was called.
        when(endpointService.createMonitoredEndpoint(any(), any(), any()))
            .thenReturn(mock(MonitoredEndpoint.class));

        addAuthorizedToken(request);

        mockMvc.perform(request)
            .andExpect(status().isOk());

        verify(endpointService, times(1))
            .createMonitoredEndpoint(AUTHORIZED_USER, url, interval);
    }

}
