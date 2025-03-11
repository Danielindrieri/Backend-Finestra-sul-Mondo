package BackEndFinestraSulMondo.Auth;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AppUserService {

    final private AppUserRepository appUserRepository;

    final private PasswordEncoder passwordEncoder;

    final private AuthenticationManager authenticationManager;

    final private JwtTokenUtil jwtTokenUtil;

    public AppUser registerUser(
            String username,
            String password,
            String firstName,
            String lastName,
            String email,
            Set<Role> roles) {
        // Check if the username already exists
        if (appUserRepository.existsByUsername(username)) {
            throw new EntityExistsException("Username già in uso");
        }

        // Check if the email already exists
        if (appUserRepository.existsByEmail(email)) {
            throw new EntityExistsException("Email già in uso");
        }

        // Create a new AppUser instance and set the fields
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(passwordEncoder.encode(password));
        appUser.setFirstName(firstName);
        appUser.setLastName(lastName);
        appUser.setEmail(email);
        appUser.setRoles(roles);

        // Save and return the AppUser
        return appUserRepository.save(appUser);
    }

    public Optional<AppUser> findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    public String authenticateUser(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            AppUser appUser = appUserRepository.findByUsername(username)
                    .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));
            return jwtTokenUtil.generateToken(userDetails, appUser.getFirstName(), appUser.getAvatar());
        } catch (AuthenticationException e) {
            throw new SecurityException("Credenziali non valide", e);
        }
    }

    public AppUser loadUserByUsername(String username) {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato con username: " + username));

        return appUser;
    }

    public void registerUser(AppUser appUser) {

        if (appUserRepository.findByUsername(appUser.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }

        if (appUserRepository.findByEmail(appUser.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        String encodedPassword = passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        if (appUser.getRoles() == null || appUser.getRoles().isEmpty()) {
            appUser.setRoles(Set.of(Role.ROLE_USER));
        }

        appUserRepository.save(appUser);
    }
}
