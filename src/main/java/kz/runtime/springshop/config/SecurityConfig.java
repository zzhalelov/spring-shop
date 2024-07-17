package kz.runtime.springshop.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/products/create").authenticated();
            auth.requestMatchers("/cart").authenticated();
            auth.requestMatchers("/categories").authenticated();
            auth.requestMatchers("/categories/create").authenticated();
            auth.requestMatchers("/orders/list").authenticated();
            auth.requestMatchers("/products/update/**").hasRole("admin");
            auth.anyRequest().permitAll();
        });

        http.formLogin(formLogin -> {
            formLogin.loginPage("/login");
            formLogin.defaultSuccessUrl("/products");
            formLogin.failureUrl("/login?error=true");
        });

        http.logout(logout -> {
            logout.logoutUrl("/logout");
            logout.logoutSuccessUrl("/login?logout=true");
            logout.invalidateHttpSession(true);
            logout.deleteCookies("JSESSIONID");
        });

        http.exceptionHandling(exceptionHandling ->
                exceptionHandling.accessDeniedPage("/login"));

        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}