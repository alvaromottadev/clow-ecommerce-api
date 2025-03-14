package br.com.motta.ecommerce.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthenticatorFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/cliente/get").permitAll()
                        .requestMatchers("/cliente/get/**").hasRole("ADMIN")
                        .requestMatchers("/cliente/get-all").hasRole("ADMIN")

                        .requestMatchers("/cliente/register").permitAll()
                        .requestMatchers("/cliente/register/").permitAll()

                        .requestMatchers("/cliente/atualizar").authenticated()
                        .requestMatchers("/cliente/atualizar/**").hasRole("ADMIN")

                        .requestMatchers("/cliente/deletar").authenticated()
                        .requestMatchers("/cliente/deletar/**").hasRole("ADMIN")

                        .requestMatchers("/produto/deletar/**").hasRole("ADMIN")
                        .requestMatchers("/produto/get-all").permitAll()
                        .requestMatchers("/produto/cadastrar").hasRole("ADMIN")
                        .requestMatchers("/produto/atualizar/**").hasRole("ADMIN")

                        .requestMatchers("/carrinho/adicionar/**").permitAll()
                        .requestMatchers("/carrinho/remover/**").permitAll()
                        .requestMatchers("/carrinho/deletar/**").permitAll()
                        .requestMatchers("/carrinho/get").permitAll()
                        .requestMatchers("/carrinho/get/**").hasRole("ADMIN")

                        .requestMatchers("/pedido/efetuar-pedido").authenticated()
                        .requestMatchers("/pedido/get/**").authenticated()
                        .requestMatchers("/pedido/get-all/**").hasRole("ADMIN")

                        .requestMatchers("/estoque/**").hasRole("ADMIN")

                        .requestMatchers("/payment").permitAll()

                        .requestMatchers("/auth/login").permitAll()
                        .anyRequest().authenticated()
                );
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}