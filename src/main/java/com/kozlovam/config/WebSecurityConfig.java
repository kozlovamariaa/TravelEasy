package com.kozlovam.config;


import com.kozlovam.dto.UserDTO;
import com.kozlovam.security.JWTAuthorizationFilter;
import com.kozlovam.utils.HibernateSessionFactoryUtil;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public JWTAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDTO UserDTO(){
        return new UserDTO();
    }

    @Bean
    public HibernateSessionFactoryUtil hibernateSessionFactoryUtil(){
        return new HibernateSessionFactoryUtil();
    }
    @Bean
    JSONObject JSONObject(){
        return new JSONObject();
    }

    @Bean
    public ModelMapper ModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public RestTemplate RestTemplate(){
        return new RestTemplate();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.httpBasic().disable().formLogin().disable();
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/registration").permitAll()
                .anyRequest().authenticated();
    }
}
