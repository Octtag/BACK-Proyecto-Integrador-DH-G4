package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Service;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Entities.AppUser;
import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Repository.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
