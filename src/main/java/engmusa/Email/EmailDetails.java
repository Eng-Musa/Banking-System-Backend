package engmusa.Email;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class EmailDetails {
    private String email;
    private String name;
    private String token;
    private AtomicInteger accountNumber;
}
