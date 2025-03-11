package BackEndFinestraSulMondo.Auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Order(2)
@RequiredArgsConstructor
@Slf4j
public class AuthRunner implements ApplicationRunner {

    final private AppUserService appUserService;
    final private PasswordEncoder passwordEncoder;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // Verifica se è necessario creare la tabella
        log.info("*** spring.jpa.hibernate.ddl-auto set to '{}'. {}", ddlAuto,
                ddlAuto.equals("none") ? "Skipping records creation..." : "Creating records...");
        if (ddlAuto.equals("none")) {
            return;
        }

        // Creazione dell'admin
        log.info("Creazione Admin...");
        AppUser admin = new AppUser();
        admin.setFirstName("Admin");
        admin.setLastName("Super");
        admin.setUsername("admin");
        admin.setEmail("admin@example.com");
        admin.setPassword(passwordEncoder.encode("adminpassword123"));
        admin.setRoles(Set.of(Role.ROLE_ADMIN));

        try {
            appUserService.registerUser(admin);
            log.info("Admin user {} created successfully.", admin.getUsername());
        } catch (IllegalArgumentException e) {
            log.error("Errore: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Eccezione: {}", e.getMessage());
        }

        // Creazione completata
        log.info("Admin user created successfully.");
    }
}
