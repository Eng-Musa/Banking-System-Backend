package engmusa.Controllers;

import engmusa.Transactions.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestParam("depositAmount") float depositAmount){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            String email = ((UserDetails) principal).getUsername();
            Float  newBalance = transactionServices.Deposit(email, depositAmount);
            return ResponseEntity.ok("The new balance after deposit is: " + newBalance);
        }
        return ResponseEntity.notFound().build();
    }
}
