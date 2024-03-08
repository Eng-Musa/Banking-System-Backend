package engmusa.DTOs;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private int idNumber;
    private Date dateOfBirth;
    private LocalDateTime dateOfCreation;
    private String password;
}
