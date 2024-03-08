package engmusa.Services.Impements;

import engmusa.DTOs.SignUpRequest;
import engmusa.DTOs.UserDTO;
import engmusa.Models.User;
import engmusa.Repository.UserRepository;
import engmusa.Services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class SignUpRequestImpl implements SignUpService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDTO createUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setIdNumber(signUpRequest.getIdNumber());
        user.setDateOfBirth(signUpRequest.getDateOfBirth());
        user.setDateOfCreation(LocalDateTime.now());
        user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        User createdUser = userRepository.save(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(createdUser.getId());
        userDTO.setFirstName(createdUser.getFirstName());
        userDTO.setLastName(createdUser.getLastName());
        userDTO.setIdNumber(createdUser.getIdNumber());
        userDTO.setDateOfBirth(createdUser.getDateOfBirth());
        userDTO.setPassword(createdUser.getPassword());
        userDTO.setDateOfCreation(createdUser.getDateOfCreation());
        return userDTO;
    }
}
