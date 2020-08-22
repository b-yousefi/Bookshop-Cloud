package b_yousefi.bookshop.usersws.security;

import b_yousefi.bookshop.usersws.data.User;
import b_yousefi.bookshop.usersws.service.UserService;
import b_yousefi.bookshop.usersws.ui.model.JwtRequest;
import b_yousefi.bookshop.usersws.ui.model.JwtResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by: b.yousefi
 * Date: 8/10/2020
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;
    private Environment environment;

    public AuthenticationFilter(UserService userService, Environment environment, AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
        this.userService = userService;
        this.environment = environment;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            JwtRequest cred = new ObjectMapper().readValue(request.getInputStream(), JwtRequest.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            cred.getUsername(),
                            cred.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain
            , Authentication auth)
            throws IOException {
        String username = ((User) auth.getPrincipal()).getUsername();
        User userDetails = userService.getUserDetailByUserName(username);
        String token = Jwts.builder()
                .setSubject(userDetails.getId())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
                .compact();

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        JwtResponse jwtResponse = new JwtResponse(token, userDetails.getId());
        objectMapper.writeValue(out, jwtResponse);
        out.flush();
    }
}
