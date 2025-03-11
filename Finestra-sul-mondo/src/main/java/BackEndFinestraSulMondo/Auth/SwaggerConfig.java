package BackEndFinestraSulMondo.Auth;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth"; // 🔹 Nome standardizzato
        return new OpenAPI()
                .info(new Info().title("API Documentation")
                        .version("1.0")
                        .description("Documentazione delle API con autenticazione JWT"))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName)) // 🔹 Usa il nome corretto
                .components(new Components().addSecuritySchemes(securitySchemeName,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Inserisci il token JWT nel formato: Bearer {token}")));
    }
}
