package BackEndFinestraSulMondo.Auth;
import lombok.Data;

import java.util.Set;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Role> roles;
}
