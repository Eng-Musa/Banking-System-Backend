package engmusa.Controllers;

import engmusa.DTOs.SignInRequest;
import engmusa.DTOs.SignInResponse;
import engmusa.Models.User;
import engmusa.Repository.UserRepository;
import engmusa.Services.Impements.SignInServiceImpl;
import engmusa.Services.SignUpService;
import engmusa.Utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SignInController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SignInServiceImpl signInService;
    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> signIn (@RequestBody SignInRequest signInRequest){
        try{
            UserDetails userDetails = signInService.loadUserByUsername(
                    signInRequest.getEmail());

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInRequest.getEmail(),
                            signInRequest.getPassword()));
            final String jwt = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new SignInResponse(jwt));
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(signInRequest.getEmail() + " does not exist, kindly register!");
        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }catch (DisabledException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Kindly verify account first, check email sent to " +signInRequest.getEmail());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during authentication");
        }
    }
}
