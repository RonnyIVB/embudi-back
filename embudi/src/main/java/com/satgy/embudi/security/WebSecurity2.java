package com.satgy.embudi.security;

import com.satgy.embudi.repository.UserPasswordRepositoryI;
import com.satgy.embudi.service.UserPasswordServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurity {
    private final UserPasswordServiceImp userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserPasswordRepositoryI userRepository;

    @Autowired
    public WebSecurity(@Qualifier("userService") UserPasswordServiceImp userService,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       UserPasswordRepositoryI userRepository) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("/register").permitAll();
                    request.requestMatchers("/users")
                            .hasAnyAuthority("USER", "ADMIN");
                }).formLogin(Customizer.withDefaults()).build();

    }

    @Bean
    public SecurityFilterChain2 configure(HttpSecurity http) throws Exception {
        // Configure AuthenticationManagerBuilder
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
        // Get AuthenticationManager
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        http
                .cors().and().csrf().disable()
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .anyRequest().authenticated()
                .and()
                // User Authentication with custom login URL path
                .addFilter(getAuthenticationFilter(authenticationManager))
                // User Authorization with JWT
                .addFilter(new AuthorizationFilter(authenticationManager, userRepository))
                .authenticationManager(authenticationManager)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.headers().frameOptions().disable();
        return http.build();
    }

    // Configure custom login URL path
    protected AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) {
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager);
        filter.setFilterProcessesUrl("/users/login");
        return filter;
    }
}