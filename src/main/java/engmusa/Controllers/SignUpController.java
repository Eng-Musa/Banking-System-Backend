package engmusa.Controllers;

import engmusa.DTOs.SignUpRequest;
import engmusa.DTOs.UserDTO;
import engmusa.Services.Impements.SignInServiceImpl;
import engmusa.Services.SignUpService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SignUpController {
    @Autowired
    private SignUpService signUpService;
    @Autowired
    private SignInServiceImpl signInService;
    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody @Valid SignUpRequest signUpRequest){
        UserDTO createdUser = signUpService.createUser(signUpRequest);
        if(createdUser == null){
            return new ResponseEntity<>("System error, retry!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmUser (@RequestParam("token") String token){
        return new ResponseEntity<>(signUpService.confirmToken(token), HttpStatus.ACCEPTED);
    }


}
