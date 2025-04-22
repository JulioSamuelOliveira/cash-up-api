package br.com.fiap.cash_up_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{ //filtros para as configurações de seguranças
        return http
        .authorizeHttpRequests(auth -> auth  //define se o usuario pode ou nao acessar a rota
            .requestMatchers("/categories/**").hasAnyRole("USER", "ADMIN")
            .requestMatchers("/transactionS/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/user/**").authenticated()
            .requestMatchers(HttpMethod.DELETE, "user/**").hasRole("ADMIN")
            .requestMatchers("/swagger-ui/**").permitAll()
            .requestMatchers("/v3/api-docs/**").permitAll()
        )
        .csrf(csrf -> csrf.disable()) //disabilita o csrf para o metodo POST
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //faz com que o usuario não fique logado
        .httpBasic(Customizer.withDefaults())
        .build();
    }

    @Bean //faz com que o metodo seja alcançado pelo spring
    UserDetailsService userDetailsService(){ //detalhes dos usuários que poderão acessar a api
        return new InMemoryUserDetailsManager(
            User.withUsername("julio").password("$2a$12$dZHvqM36RfRdDeA7c8s7kOF37KTb3L1T/a/xY2nLlXzgFSUaLKkNG").roles("ADMIN", "USER").build(),
            User.withUsername("duda").password("$2a$12$Vua35kPxau6rvlp4iua1R.y3VByaNaFyOG0j6.79pG5KmGInu/V96").roles("USER").build()
        );
    }

    @Bean //faz com que o metodo seja alcançado pelo spring
    PasswordEncoder passwordEncoder(){   //para aceitar a senha criptografada
        return new BCryptPasswordEncoder();
    }

}
