package engmusa.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String idNumber;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime dateOfCreation;
    private String password;
    private Boolean enabled = false;
    @Column(unique = true, updatable = false)
    private AtomicInteger accountNumber = new AtomicInteger(3650000);
    public User() {
        this.accountNumber = new AtomicInteger(accountNumber.incrementAndGet());
    }
}
