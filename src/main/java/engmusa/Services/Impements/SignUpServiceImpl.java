package engmusa.Services.Impements;

import engmusa.DTOs.SignUpRequest;
import engmusa.DTOs.UserDTO;
import engmusa.Email.EmailDetails;
import engmusa.Email.EmailService;
import engmusa.Models.ConfirmationToken;
import engmusa.Models.User;
import engmusa.Repository.UserRepository;
import engmusa.Services.ConfirmationTokenService;
import engmusa.Services.SignUpService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SignUpServiceImpl implements SignUpService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConfirmationTokenService tokenService;
    @Autowired
    private EmailService emailService;
    @Override
    public UserDTO createUser(SignUpRequest signUpRequest) {
        try {

            User existingUser = userRepository.findFirstByEmail(signUpRequest.getEmail());
            if(existingUser != null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with "
                        +signUpRequest.getEmail().toLowerCase()+ " already exists");
            }
            User user = new User();
            user.setFirstName(signUpRequest.getFirstName());
            user.setLastName(signUpRequest.getLastName());
            user.setEmail(signUpRequest.getEmail().toLowerCase());
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
            userDTO.setAccountNumber(createdUser.getAccountNumber());

            String token = UUID.randomUUID().toString();
            ConfirmationToken confirmationToken = new ConfirmationToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(2),
                    user
            );
            tokenService.saveConfirmationToken(confirmationToken);

            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setEmail(createdUser.getEmail());
            emailDetails.setName(createdUser.getFirstName());
            emailDetails.setToken(token);
            emailService.sendEmail(emailDetails);

            return userDTO;
        }catch (ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    @Transactional
    public String confirmToken(String token) {
        try{
            ConfirmationToken confirmationToken = tokenService.getToken(token)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid token"));
            LocalDateTime expiresAt = confirmationToken.getExpiresAt();
            if(expiresAt.isBefore(LocalDateTime.now())){
                return "Token expired!";
            }else{
                User user = confirmationToken.getUser();
                user.setEnabled(true);
                userRepository.save(user);
                return "Account confirmed successfully! \n Proceed to login.";
            }
        }catch (IllegalArgumentException e){
            return "Invalid token!";
        }catch (Exception e){
            return "Failed to confirm account: " + e.getMessage();
        }
    }
}
