package com.apu.TcpServerForAccessControlMVC.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.apu.TcpServerForAccessControlDB.repository.SystemUserRepository;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
//        .antMatchers("/oauth/**").permitAll()
//        .antMatchers("/api/**").permitAll()
        .antMatchers("/login").permitAll()
        .antMatchers("/registration").permitAll()
        .antMatchers("/admin/**").hasAuthority("ADMIN").anyRequest()
        .authenticated().and().csrf().disable().formLogin()
        .loginPage("/login").failureUrl("/login?error=true")
        .defaultSuccessUrl("/card/view")
        .usernameParameter("email")
        .passwordParameter("password")
        .and().logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/").and().exceptionHandling()
        .accessDeniedPage("/access-denied");
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider();
    }
    
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Autowired
    SystemUserRepository userRepository;
    
    @Bean 
    public UserDetailsService userDetailsService() {
        return new ApplicationUserDetailsService(userRepository);
    }
    
}
