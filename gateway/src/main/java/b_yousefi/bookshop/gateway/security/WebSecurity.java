package b_yousefi.bookshop.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Created by: b.yousefi
 * Date: 8/10/2020
 */
@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private final Environment environment;

    @Autowired
    public WebSecurity(Environment environment) {
        this.environment = environment;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, environment.getProperty("api.login.url.path")
                        , environment.getProperty("api.registration.url.path")).permitAll()
                .antMatchers(HttpMethod.GET, environment.getProperty("api.users.url.path")).permitAll()
                .antMatchers(HttpMethod.PATCH, environment.getProperty("api.users.url.path")).permitAll()
                .antMatchers(
                        environment.getProperty("api.users.swagger.doc.url.path"),
                        environment.getProperty("api.users.actuator.url.path"),
                        environment.getProperty("api.users.swagger.uip.url.path"),
                        environment.getProperty("api.users.swagger.ui.url.path")).permitAll()
                .antMatchers(environment.getProperty("api.zuul.actuator.url.path")).permitAll()
                .anyRequest().authenticated()
                .and().addFilter(new AuthorizationFilter(authenticationManager(), environment));

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
