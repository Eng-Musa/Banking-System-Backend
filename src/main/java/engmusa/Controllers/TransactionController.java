package engmusa.Controllers;

import engmusa.Transactions.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class TransactionController {

    @Autowired
    private TransactionServices transactionServices;
    @GetMapping("balance")
    public ResponseEntity<String> checkBalance(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            String email = ((UserDetails) principal).getUsername();
            Float balance = transactionServices.getBalance(email);
            return ResponseEntity.ok("The balance is: " + balance);
        }
        return ResponseEntity.notFound().build();
    }
}
