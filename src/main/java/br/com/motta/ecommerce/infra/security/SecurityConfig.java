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
//                        //Produtos
//                        .requestMatchers("/produto").permitAll()
//                        .requestMatchers("/produto/cadastrar").hasRole("ADMIN")
//                        .requestMatchers("/produto/atualizar").hasRole("ADMIN")
//                        .requestMatchers("/produto/get-all").permitAll()
//                        .requestMatchers("/produto/deletar/**").hasRole("ADMIN")
//
//                        //Carrinho
//                        .requestMatchers("/carrinho/**").permitAll()
//
//                        //Usuario
//                        .requestMatchers("/usuario/atualizar").hasRole("ADMIN")
//                        .requestMatchers("/usuario/get-all").hasRole("ADMIN")
//                        .requestMatchers("/usuario/get/**").permitAll()
//                        .requestMatchers("/usuario/deletar/**").hasRole("ADMIN")
//
//                        //Email
//                        .requestMatchers("/email").hasRole("ADMIN")


                        .requestMatchers("/auth/login").permitAll()  // endpoint de login público
                        .requestMatchers("/usuario/**").permitAll()
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/context-path/**", "/api-docs").permitAll()  // Permite acesso ao Swagger
                        .anyRequest().authenticated() // Requer autenticação para as demais requisições
                );

        // Adiciona o filtro JWT antes do filtro padrão de autenticação
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