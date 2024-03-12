package engmusa.Services.Impements;

import engmusa.DTOs.SignUpRequest;
import engmusa.DTOs.UserDTO;
import engmusa.Models.User;
import engmusa.Repository.UserRepository;
import engmusa.Services.SignUpService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
@Service
public class SignUpServiceImpl implements SignUpService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDTO createUser(SignUpRequest signUpRequest) {
        try {
            User user = new User();
            user.setFirstName(signUpRequest.getFirstName());
            user.setLastName(signUpRequest.getLastName());
            user.setEmail(signUpRequest.getEmail());
            user.setIdNumber(signUpRequest.getIdNumber());
            user.setDateOfBirth(signUpRequest.getDateOfBirth());
            user.setDateOfCreation(LocalDateTime.now());
            user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
            User createdUser = userRepository.save(user);

            UserDTO userDTO = new UserDTO();
            userDTO.setId(createdUser.getId());
            userDTO.setFirstName(createdUser.getFirstName());
            userDTO.setLastName(createdUser.getLastName());
            userDTO.setEmail(createdUser.getEmail());
            userDTO.setIdNumber(createdUser.getIdNumber());
            userDTO.setDateOfBirth(createdUser.getDateOfBirth());
            userDTO.setPassword(createdUser.getPassword());
            userDTO.setDateOfCreation(createdUser.getDateOfCreation());
            return userDTO;
        }catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
