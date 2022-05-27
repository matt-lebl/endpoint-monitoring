package ca.lebl.monitoring.security;

import ca.lebl.monitoring.entity.User;
import ca.lebl.monitoring.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthTokenFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    public AuthTokenFilter(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        String token;

        try {
            token = request.getHeader("Authorization").split(" ")[1];
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            Map<String, Object> body = new HashMap<>();
            body.put("error", "Please provide a valid accessToken.");
            response.getOutputStream().println(objectMapper.writeValueAsString(body));

            return;
        }

        User user = userService.getUserByAccessToken(token);

        if (user == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            Map<String, Object> body = new HashMap<>();
            body.put("error", "The provided accessToken is not valid.");
            response.getOutputStream().println(objectMapper.writeValueAsString(body));

            return;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            user,
            null,
            null
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
