package be.ocrapi.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserRequest {

    private String name;

    private String email;

    private String password;

    private String gender;

    private String phone;

    private String avatar;

    private String access_token;

    private String refresh_token;
}
