package io.swagger.configuration;


import io.swagger.security.JwtConfigurer;
import io.swagger.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class APISecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/Users/Accounts/**").hasRole("USER")
//                .antMatchers(HttpMethod.GET, "/Admin/Accounts/**").hasRole("EMPLOYEE")
//                .antMatchers(HttpMethod.GET, "/v1/vehicles/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/Admin/Accounts/**").hasRole("EMPLOYEE")
//                .antMatchers(HttpMethod.GET, "/Admin/Accounts/**").hasRole("EMPLOYEE")
//                .antMatchers(HttpMethod.GET, "/Admin/Accounts/**").hasRole("EMPLOYEE")
//                .antMatchers(HttpMethod.GET, "/Admin/Accounts/**").hasRole("EMPLOYEE")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
        //@formatter:on
    }
}
