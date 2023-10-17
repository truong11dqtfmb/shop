package com.dqt.shop.security.jwt;

import com.dqt.shop.constant.Constant;
import com.dqt.shop.entity.User;
import com.dqt.shop.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class AuthTokenFilter extends OncePerRequestFilter {
    private static Logger log = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (request.getServletPath().contains(Constant.URL_AUTH) || request.getServletPath().contains(Constant.URL_SWAGGER) || request.getServletPath().contains(Constant.URL_API_DOC) || request.getServletPath().contains(Constant.URL_SOCKET) || request.getServletPath().equals("/")) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = parseJwt(request);
            if (token != null && jwtUtils.validateJwtToken(token)) {
                String email = jwtUtils.getUserNameFromJwtToken(token);
                Optional<User> userOptional = userRepository.findByEmail(email);
//            Authorization

                if (!userOptional.isPresent()) {
                    response.setStatus(UNAUTHORIZED.value());
                    Map<String, String> notAuth = new HashMap<>();
                    notAuth.put("error_message", "Email or password incorrect");
                    new ObjectMapper().writeValue(response.getOutputStream(), notAuth);
                }
                User user = userOptional.get();
                if (!user.getStatus()) {
                    response.setStatus(UNAUTHORIZED.value());
                    Map<String, String> notAuth = new HashMap<>();
                    notAuth.put("error_message", "User is disabled");
                    new ObjectMapper().writeValue(response.getOutputStream(), notAuth);
                }

//            log request
                filterChain.doFilter(request, response);
                return;
            } else {
                response.setStatus(UNAUTHORIZED.value());
                Map<String, String> notAuth = new HashMap<>();
                notAuth.put("error_message", "Email or password incorrect");
                new ObjectMapper().writeValue(response.getOutputStream(), notAuth);
                return;
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(AUTHORIZATION);

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(Constant.TOKEN_TYPE)) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
}
