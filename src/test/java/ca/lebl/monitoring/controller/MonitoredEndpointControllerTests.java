package ca.lebl.monitoring.controller;

import ca.lebl.monitoring.service.MonitoredEndpointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

}
