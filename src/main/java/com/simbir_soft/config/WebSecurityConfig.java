package com.simbir_soft.config;

import com.simbir_soft.model.Permission;
import com.simbir_soft.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/js/**", "/api/registrations", "/css/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/message").hasAuthority(Permission.SEND_MESSAGE.getPermission())
                .antMatchers(HttpMethod.DELETE, "/api/message").hasAuthority(Permission.DELETE_MESSAGE.getPermission())
                .antMatchers(HttpMethod.POST, "/api/rooms").hasAuthority(Permission.CREATE_ROOM.getPermission())
                .antMatchers(HttpMethod.POST, "/api/rooms").hasAuthority(Permission.RENAME_ROOM.getPermission())
                .antMatchers(HttpMethod.POST, "/api/rooms").hasAuthority(Permission.CONNECT_USER.getPermission())
                .antMatchers(HttpMethod.POST, "/api/rooms").hasAuthority(Permission.DISCONNECT_USER.getPermission())
                .antMatchers(HttpMethod.DELETE, "/api/rooms").hasAuthority(Permission.DELETE_ROOM.getPermission())
                .antMatchers(HttpMethod.POST, "/api/user").hasAuthority(Permission.RENAME_USER.getPermission())
                .antMatchers(HttpMethod.POST, "/api/user").hasAuthority(Permission.BAN_USER.getPermission())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/main", true)
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        return daoAuthenticationProvider;
    }
}
