package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.AppUser;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Transactional
    public AppUser guardarAppUser(AppUser appUser){
        return appUserRepository.save(appUser);
    }

    public Boolean existsByEmail(String email){
        return appUserRepository.existsByEmail(email);
    }

    public Optional<AppUser> findByEmail(String email){
        return Optional.ofNullable(appUserRepository.findByEmail(email).orElse(null));
    }

    public Optional<AppUser> findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Crear una lista de GrantedAuthority a partir de los roles del SimpleUser
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(appUser.getRol().name()));

        // Crear y retornar una instancia de User (de Spring Security) utilizando los detalles de SimpleUser
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }

    public AppUser findById(Long vendedorId) {
        return appUserRepository.findById(vendedorId).get();
    }
}
