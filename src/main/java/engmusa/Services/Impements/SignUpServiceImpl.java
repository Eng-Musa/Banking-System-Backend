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
import java.util.Optional;
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
            user.setAccountNumber(accountNoGenerator());
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

            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setEmail(createdUser.getEmail());
            emailDetails.setName(createdUser.getFirstName());
            emailDetails.setToken(tokenGeneration(user));
            //emailService.sendConfirmationEmail(emailDetails);

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

            User user = confirmationToken.getUser();

            if(user.getEnabled()){
                return "Account already confirmed, kindly login";
            }

            LocalDateTime expiresAt = confirmationToken.getExpiresAt();
            if(expiresAt.isBefore(LocalDateTime.now())){
                EmailDetails emailDetails = new EmailDetails();
                emailDetails.setEmail(user.getEmail());
                emailDetails.setName(user.getFirstName());
                emailDetails.setToken(tokenGeneration(user));
                emailService.sendConfirmationEmail(emailDetails);
                return "Token expired! Recheck email for new token";
            }

            user.setEnabled(true);
            confirmationToken.setConfirmedAt(LocalDateTime.now());
            userRepository.save(user);


            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setEmail(user.getEmail());
            emailDetails.setName(user.getFirstName());
            emailDetails.setAccountNumber(user.getAccountNumber());
            emailService.sendSuccessfulCreationEmail(emailDetails);
            return "Account confirmed successfully! \n Proceed to login.";
        }catch (IllegalArgumentException e){
            return "Invalid token!";
        }catch (Exception e){
            return "Failed to confirm account: " + e.getMessage();
        }
    }
    
    @Override
    public String tokenGeneration(User user) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5),
                user
        );
        tokenService.saveConfirmationToken(confirmationToken);

        return token;
    }
    @Override
    public Integer accountNoGenerator() {
        Optional<User> user = Optional.ofNullable(userRepository.findFirstByAccountNumber(365000));
        if (user.isEmpty()) {
            return 365000;
        } else {
            Integer maxAccountNumber = userRepository.findMaxAccountNumber();
            if (maxAccountNumber != null) {
                return maxAccountNumber + 1;
            } else {
                return 365000;
            }
        }
    }

}
