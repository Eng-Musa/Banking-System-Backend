package engmusa.Services;

import engmusa.DTOs.SignUpRequest;
import engmusa.DTOs.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


public interface SignUpService {
    UserDTO createUser(SignUpRequest signUpRequest);
    String confirmToken(String token);

}
