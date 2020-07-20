package com.fiek.travelGuide.config;

import com.fiek.travelGuide.service.UserSecurityService;
import com.fiek.travelGuide.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;

    @Autowired
    private UserSecurityService userSecurityService;

    private BCryptPasswordEncoder passwordEncoder() {
        return SecurityUtility.passwordEncoder();
    }

    private static final String[] PUBLIC_MATCHERS={
            "/css/**",
            "/js/**",
            "/images/**",
            "/",
            "/newAccount",
            "/forgetPassword",
            "/login",
            "/admin",
            "/fonts/**",
            "/destinationList",
            "/searchByMunicipality",
            "/searchLocation"

    };

    @Override
    protected void configure(HttpSecurity http) throws Exception{
//        http
//                .headers()
//                .addHeaderWriter(new StaticHeadersWriter("Content-Security-Policy", "default-src 'none'; script-src 'self' 'unsafe-inline' 'unsafe-eval'; connect-src 'self'; img-src 'self'; style-src 'self' 'unsafe-inline'; object-src 'none'; "));

//        .headers()
//                .contentSecurityPolicy("img-src 'self' https://*.googleusercontent.com/ https://*.paypalobjects.com https://ak1s.abmr.net " +
//                        "https://ak1s.mathtag.com https://akamai.mathtag.com https://ak1.abmr.net https://*.paypal.com;");
        http.authorizeRequests().
//                antMatchers("/**").
                antMatchers(PUBLIC_MATCHERS).
                permitAll().anyRequest().authenticated();

        http
                .csrf().disable().cors().disable()
                .formLogin().failureUrl("/login?error").defaultSuccessUrl("/")
                .loginPage("/login").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/?logout").deleteCookies("remember-me").permitAll()
                .and()
                .rememberMe();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
    }
}
