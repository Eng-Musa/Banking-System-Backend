package engmusa.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import engmusa.Validators.Age;
import engmusa.Validators.IdNumber;
import engmusa.Validators.Name;
import engmusa.Validators.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
@Data
public class SignUpRequest {
    @NotBlank(message = "First Name cannot be null or empty!")
    @Name
    private String firstName;
    @NotBlank(message = "Last Name cannot be null or empty!")
    @Name
    private String lastName;
    @NotBlank(message = "Email cannot be null or empty!")
    @Email(message = "Invalid email")
    private String email;
    @Positive(message = "Id Number must be a positive non-zero integer!")
    @IdNumber
    private String idNumber;
    @Age
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;
    @NotBlank(message = "Password cannot be null or empty!")
    @Password
    private String password;
}
