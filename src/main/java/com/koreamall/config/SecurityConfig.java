package com.koreamall.config;


import com.koreamall.hanlder.CustomOAuth2SuccessHandler;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable);

        http.oauth2Login(config -> {
            config.loginPage("/user/login")
                    .defaultSuccessUrl("/main")
                    .successHandler(new CustomOAuth2SuccessHandler())
                    .permitAll();
        });


        http.formLogin(config -> {
            config.loginPage("/user/login")
                    .usernameParameter("id")
                    .defaultSuccessUrl("/main");
        });

        http.logout(config -> {
            config.logoutUrl("/user/logout")
                    .logoutSuccessUrl("/main")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .permitAll();
        });

        http.authorizeHttpRequests(registry -> {
            //main 경로는 인증이 되어야 한다
//            registry.requestMatchers("/main").authenticated()
            registry.anyRequest().permitAll(); // 그 외 모든 경로는 인증 없이 가능
//            registry.anyRequest().authenticated();
        });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }


}
