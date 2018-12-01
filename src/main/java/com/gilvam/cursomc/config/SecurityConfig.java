package com.gilvam.cursomc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;

    private static final String[] PUBLIC_MATCHERS = {
            "/h2-console/**"
    };

    private static final String[] PUBLIC_MATCHERS_GET = {
            "/products/**",
            "/categories/**",
            "/clients/**"
    };

    private static final String[] PUBLIC_MATCHERS_POST = {
            "/clients",
            "/auth/forgot/**"
    };


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //se @Profile("test"), libera acesso ao h2-console
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

        http.cors() // chama o CorsConfigurationSource e suas configurações
        .and().csrf().disable(); //desabilitar proteção contra ataques CSRF pois o sistema é stateless e não usa armazenamento em seção

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
                .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
                .antMatchers(PUBLIC_MATCHERS).permitAll() // libera acesso para todas as rotas em PUBLIC_MATCHERS
                .anyRequest().authenticated(); // para todo o restante, é necessário estar autenticado

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // assegura que o back-end não criará seção de usuário
    }


    /**
     * permitir requisições de múltiplas fontes (cross-origin requests)
     * @return
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    /**
     * criptografia de senha
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}