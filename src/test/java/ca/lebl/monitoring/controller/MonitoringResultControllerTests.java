package ca.lebl.monitoring.controller;

import ca.lebl.monitoring.entity.MonitoredEndpoint;
import ca.lebl.monitoring.service.MonitoredEndpointService;
import ca.lebl.monitoring.service.MonitoringResultService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MonitoringResultController.class)
public class MonitoringResultControllerTests extends ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MonitoringResultService resultService;

    @MockBean
    private MonitoredEndpointService endpointService;

    private Long VALID_ENDPOINT_ID = 1L;
    private MonitoredEndpoint SAMPLE_ENDPOINT = new MonitoredEndpoint(
        AUTHORIZED_USER,
        "http://www.google.com/",
        60
    );

    @BeforeEach
    void setupSampleEndpoint() {
        Mockito.when(endpointService.getEndpointById(VALID_ENDPOINT_ID))
            .thenReturn(SAMPLE_ENDPOINT);
    }

    @Test
    void testAuthorized() throws Exception {
        MockHttpServletRequestBuilder request = get("/results")
            .param("id", VALID_ENDPOINT_ID.toString());

        addAuthorizedToken(request);

        mockMvc.perform(request)
            .andExpect(status().isOk());
    }

    @Test
    void testMissingId() throws Exception {
        MockHttpServletRequestBuilder request = get("/results");

        addAuthorizedToken(request);

        mockMvc.perform(request)
            .andExpect(status().isBadRequest());
    }

}
