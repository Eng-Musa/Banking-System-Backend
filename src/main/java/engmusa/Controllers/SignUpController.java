package engmusa.Controllers;

import engmusa.DTOs.SignUpRequest;
import engmusa.DTOs.UserDTO;
import engmusa.Services.SignUpService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class SignUpController {
    @Autowired
    private SignUpService signUpService;
    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody @Valid SignUpRequest signUpRequest){
        UserDTO createdUser = signUpService.createUser(signUpRequest);
        if(createdUser == null){
            return new ResponseEntity<>("System error, retry!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
