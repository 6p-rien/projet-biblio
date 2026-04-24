package fr.formation.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.formation.dao.IDAOUtilisateur;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    @Autowired
    private IDAOUtilisateur daoUilisateur;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.daoUilisateur
            .findByUsername(username)
            .map(u -> User.builder()
                .username(username)
                .password(u.getPassword())
                .build()
            )
            .orElseThrow(() -> new UsernameNotFoundException("L'utilisateur n'existe pas!"))
        ;
    }
}
