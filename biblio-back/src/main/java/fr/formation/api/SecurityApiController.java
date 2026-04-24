package fr.formation.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.dao.IDAOUtilisateur;
import fr.formation.dto.request.AuthRequest;
import fr.formation.dto.request.SubscriptionRequest;
import fr.formation.dto.response.AuthResponse;
import fr.formation.dto.response.EntityCreatedOrUpdatedResponse;
import fr.formation.model.Utilisateur;
import fr.formation.security.jwt.JwtUtils;

import jakarta.validation.Valid;

@RestController
public class SecurityApiController {
    private final static Logger log = LoggerFactory.getLogger(SecurityApiController.class);
    private final AuthenticationManager authenticationManager;
    private final IDAOUtilisateur daoUtilisateur;
    private final PasswordEncoder passwordEncoder;

    public SecurityApiController(AuthenticationManager authenticationManager, IDAOUtilisateur daoUtilisateur, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.daoUtilisateur = daoUtilisateur;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/api/auth")
    public AuthResponse auth(@Valid @RequestBody AuthRequest request) {
        try {
            log.debug("Tentative d'authentification ...");

            Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.debug("Authentification validée !");

            return new AuthResponse(true, JwtUtils.generate(authentication));
        }

        catch (BadCredentialsException ex) {
            log.error("Authentification impossible : mauvais identifiants.");
        }

        catch (Exception ex) {
            log.error("Authentification impossible : erreur ({}).", ex.getClass().getSimpleName());
        }

        return new AuthResponse(false, "");
    }


    @PostMapping("/api/inscription")
    public EntityCreatedOrUpdatedResponse subscribe(@Valid @RequestBody SubscriptionRequest request) {
        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setUsername(request.getUsername());
        utilisateur.setPassword(this.passwordEncoder.encode(request.getPassword()));

        utilisateur.setAdmin(false);

        this.daoUtilisateur.save(utilisateur);

        return new EntityCreatedOrUpdatedResponse(utilisateur.getId());
    }
}
