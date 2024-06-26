package pl.falynsky.hospital.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
