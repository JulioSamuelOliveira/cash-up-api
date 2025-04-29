// package br.com.fiap.cash_up_api.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// public class SecurityConfig {
    
//     @Bean
//     SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{ //filtros para as configurações de seguranças
//         return http
//         .authorizeHttpRequests(auth -> auth  //define se o usuario pode ou nao acessar a rota
//             .requestMatchers("/categories/**").hasAnyRole("USER", "ADMIN")
//             .requestMatchers("/transactionS/**").hasRole("ADMIN")
//             .requestMatchers(HttpMethod.GET, "/user/**").authenticated()
//             .requestMatchers(HttpMethod.DELETE, "user/**").hasRole("ADMIN")
//             .requestMatchers("/swagger-ui/**").permitAll()
//             .requestMatchers("/v3/api-docs/**").permitAll()
//         )
//         .csrf(csrf -> csrf.disable()) //disabilita o csrf para o metodo POST
//         .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //faz com que o usuario não fique logado
//         .httpBasic(Customizer.withDefaults())
//         .build();
//     }

//     @Bean //faz com que o metodo seja alcançado pelo spring
//     UserDetailsService userDetailsService(){ //detalhes dos usuários que poderão acessar a api
//         return new InMemoryUserDetailsManager(
//             User.withUsername("julio").password("$2a$12$dZHvqM36RfRdDeA7c8s7kOF37KTb3L1T/a/xY2nLlXzgFSUaLKkNG").roles("ADMIN", "USER").build(),
//             User.withUsername("duda").password("$2a$12$Vua35kPxau6rvlp4iua1R.y3VByaNaFyOG0j6.79pG5KmGInu/V96").roles("USER").build()
//         );
//     }

//     @Bean //faz com que o metodo seja alcançado pelo spring
//     PasswordEncoder passwordEncoder(){   //para aceitar a senha criptografada
//         return new BCryptPasswordEncoder();
//     }
//}

package br.com.fiap.cash_up_api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private AuthFilter authFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.DELETE, "/user/**").hasAuthority("ADMIN")
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login/**").permitAll()
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //.httpBasic(Customizer.withDefaults())
                .build();
    }

    // @Bean
    // UserDetailsService userDetailsService(){
    // return new InMemoryUserDetailsManager(
    // User.withUsername("joao").password("$2a$12$r3B.XzQX43dur19prADf3uMpt0SyOt5Cvl84A1JPB/ODMjHVx6Zn2").roles("ADMIN").build(),
    // User.withUsername("maria").password("$2a$12$my3rVz0UR0iAnUU6J.ZT/OxsMQ2TtcKhkS7cytjxZd/cUY/.kIiv2").roles("USER").build()
    // );
    // }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
