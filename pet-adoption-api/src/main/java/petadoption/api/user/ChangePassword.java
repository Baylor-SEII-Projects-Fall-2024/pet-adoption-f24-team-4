package petadoption.api.user;

import lombok.Getter;
import lombok.Setter;

public class ChangePassword {
    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;
}
