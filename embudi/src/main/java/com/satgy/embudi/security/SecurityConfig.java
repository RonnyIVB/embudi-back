package com.satgy.embudi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static com.satgy.embudi.general.Par.SIGN_UP_URL;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration // This annotation indicate to spring container that this class is for security when this application starts.
@EnableWebSecurity // Activate the web security in the application and also this class will contain security configs.
public class SecurityConfig {
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private JwtTokenProvider tokenProvider;

    /**
     * This method is usefully to make unit test
     * @param authenticationEntryPoint
     */
    @Autowired
    public SecurityConfig(JwtAuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    /**
     * Validate login credentials, example username and password
     * @param configuration take login credentials
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Encrypt all passwords on the application
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This bean includes json web token security filter that we created on the JwtAuthenticationFilter class
     * @return
     */
    @Bean
    public JwtAuthenticationFilter authenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    //Stablish a security filther chain in this application, this method determined the access or permission for each user. It depends of the role
    /*@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        Customizer<CsrfConfigurer<HttpSecurity>> holi = new Customizer<CsrfConfigurer<HttpSecurity>>();
        http
                .csrf(Customizer.withDefaults())
                //.csrf().disable()
                .exceptionHandling(Customizer.withDefaults()) // to able exception management
                .authenticationProvider()
                .authenticationEntryPoint(tokenProvider) // Establish custom entry point for authentication for management authentications unauthorized
                .and()
                .sessionManagement() // to able session management
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests() // all http request must be authorized
                .requestMatchers(SIGN_UP_URL + "/**").permitAll() // public resth method, unauthorized access
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }*/


        /*@Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http
                .csrf(CsrfConfigurer::disable)
                .cors(CorsConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(SIGN_UP_URL + "/**").permitAll()
                )
                .formLogin(Customizer.withDefaults())
                .build();
        }*/

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests((requests) -> requests
                        // zzzz fix the security, and put the endpoints on the authenticated secction.
                        .requestMatchers("/myBalance", "/myLoans", "/myCards").authenticated()
                        .requestMatchers(SIGN_UP_URL + "/**", "/api/user/**", "/register").permitAll()
                );
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //Make the below setting as * to allow connection from any hos
        corsConfiguration.setAllowedOrigins(List.of("*"));//(List.of("http://localhost:4200"));
        corsConfiguration.setAllowedMethods(List.of("*"));//(List.of("GET", "POST", "PUT"));
        //corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }


}
