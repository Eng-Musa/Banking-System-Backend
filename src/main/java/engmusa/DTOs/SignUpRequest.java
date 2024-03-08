package engmusa.DTOs;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
@Data
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private int idNumber;
    private Date dateOfBirth;
    private String password;
}
