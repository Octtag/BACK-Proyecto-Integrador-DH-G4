package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Jwt;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AppUserService simpleUserService;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil, AppUserService simpleUserService) {
        this.jwtUtil = jwtUtil;
        this.simpleUserService = simpleUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        //Validar que sea un header autorization valido
        String authHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        //Validar que el jwt sea valido<<
        String jwt = authHeader.split(" ")[1].trim();

        if (!this.jwtUtil.esValido(jwt)) {
            System.out.println("EL JWT ES INVALIDO O NO ESTA.");
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String email = this.jwtUtil.getEmail(jwt);
        User usuario = (User) this.simpleUserService.loadUserByUsername(email);
        System.out.println(usuario);

        //Cargar el usuario al contexto de seguridad
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                usuario.getUsername(), usuario.getPassword(), usuario.getAuthorities());


        System.out.println(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
