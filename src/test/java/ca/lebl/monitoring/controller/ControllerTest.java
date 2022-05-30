package ca.lebl.monitoring.controller;

import ca.lebl.monitoring.entity.User;
import ca.lebl.monitoring.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public abstract class ControllerTest {

    @MockBean
    private UserService userService;

    // arbitrary values used for mocking
    final String AUTHORIZED_ACCESS_TOKEN = "someAuthorizedToken";
    final String UNAUTHORIZED_ACCESS_TOKEN = "someUnauthorizedToken";
    final User AUTHORIZED_USER = new User("Testuser", "test.user@applifting.cz", AUTHORIZED_ACCESS_TOKEN);

    @BeforeEach
    void setUp() {
        Mockito.when(userService.getUserByAccessToken(AUTHORIZED_ACCESS_TOKEN))
            .thenReturn(AUTHORIZED_USER);
    }

    MockHttpServletRequestBuilder addAuthorizedToken(MockHttpServletRequestBuilder builder) {
        return builder.header("Authorization", String.format("Bearer %s", AUTHORIZED_ACCESS_TOKEN));
    }

    MockHttpServletRequestBuilder addUnauthorizedToken(MockHttpServletRequestBuilder builder) {
        return builder.header("Authorization", String.format("Bearer %s", UNAUTHORIZED_ACCESS_TOKEN));
    }

}
