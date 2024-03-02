package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Security;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.AppUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AppUserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtFilter jwtFilter;
    private final CustomHeaderValidatorFilter customHeaderValidatorFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfiguration(AppUserService appUserService, BCryptPasswordEncoder bCryptPasswordEncoder, JwtFilter jwtFilter, CustomHeaderValidatorFilter customHeaderValidatorFilter, CorsConfigurationSource corsConfigurationSource) {
        this.appUserService = appUserService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtFilter = jwtFilter;
        this.customHeaderValidatorFilter = customHeaderValidatorFilter;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Configura las peticiones para que sean redirigidas a HTTPS
                .requiresChannel()
                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                .requiresSecure()
                .and().cors().configurationSource(corsConfigurationSource).and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/mp/createAndRedirect").authenticated()
                .antMatchers(HttpMethod.POST, "/mp/notifications").permitAll()
                .antMatchers(HttpMethod.POST, "/api/eventos//{eventoId}/butacas").authenticated()
                .antMatchers("/crearEvento").permitAll()
                .anyRequest().permitAll().and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/index");
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    /*Crea un proveedor de autenticación que utiliza un servicio de detalles de
     usuario (userService) y un codificador de contraseñas (bCryptPasswordEncoder)
     para autenticar a los usuarios. ↓ ↓ ↓ */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appUserService);
        return provider;
    }

    /*Define y expone el AuthenticationManager de Spring Security,
     que es el servicio central para procesar solicitudes de autenticación ↓ ↓ ↓ */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}