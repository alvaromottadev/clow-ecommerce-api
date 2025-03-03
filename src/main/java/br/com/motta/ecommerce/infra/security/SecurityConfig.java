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

                        .requestMatchers("/usuario/get").permitAll()
                        .requestMatchers("/usuario/get/**").hasRole("ADMIN")
                        .requestMatchers("/usuario/get-all").hasRole("ADMIN")

                        //POST
                        .requestMatchers("/usuario/register").permitAll()
                        .requestMatchers("/usuario/register/").permitAll()

                        //UPDATE
                        .requestMatchers("/usuario/atualizar").authenticated()
                        .requestMatchers("/usuario/atualizar/**").hasRole("ADMIN")

                        //Corrigir
                        .requestMatchers("/usuario/deletar/**").hasRole("ADMIN")
                        .requestMatchers("/usuario/deletar").authenticated() //NÃO FUNCIONANDO

                        //Produto
                        .requestMatchers("/produto/deletar/**").hasRole("ADMIN") //OK
                        .requestMatchers("/produto/get-all").permitAll() //OK
                        .requestMatchers("/produto/cadastrar").hasRole("ADMIN") //OK
                        .requestMatchers("/produto/atualizar").hasRole("ADMIN") //OK

                        //Carrinho
                        .requestMatchers("/carrinho/adicionar/**").permitAll() // OK
                        .requestMatchers("/carrinho/remover/**").permitAll() //OK
                        .requestMatchers("/carrinho/deletar/**").permitAll()
                        .requestMatchers("/carrinho/get").permitAll()

                        //Pedido
                        .requestMatchers("/pedido/efetuar-pedido").authenticated()
                        .requestMatchers("/pedido/ver/**").authenticated() // --> Corrigir ADMIN visualizar
                        .requestMatchers("/pedido/ver-todos/**").hasRole("ADMIN")

                        //Estoque
                        .requestMatchers("/estoque/**").hasRole("ADMIN")


                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/context-path/**", "/api-docs").permitAll()  // Permite acesso ao Swagger
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