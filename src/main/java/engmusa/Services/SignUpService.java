package engmusa.Services;

import engmusa.DTOs.SignUpRequest;
import engmusa.DTOs.UserDTO;
import engmusa.Models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


public interface SignUpService {
    UserDTO createUser(SignUpRequest signUpRequest);
    String confirmToken(String token);
    String tokenGeneration(User user);
    Integer accountNoGenerator();
}
