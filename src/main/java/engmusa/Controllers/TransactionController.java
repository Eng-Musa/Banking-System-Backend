package engmusa.Controllers;

import engmusa.Models.Statement;
import engmusa.Models.User;
import engmusa.Repository.UserRepository;
import engmusa.Services.Impements.SignInServiceImpl;
import engmusa.Transactions.Enum.TransactionCosts;
import engmusa.Transactions.StatementService;
import engmusa.Transactions.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class TransactionController {

    @Autowired
    private TransactionServices transactionServices;
    @Autowired
    private StatementService statementService;
    @GetMapping("balance")
    public ResponseEntity<String> checkBalance(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            String email = ((UserDetails) principal).getUsername();
            Float balance = transactionServices.getBalance(email);
            return ResponseEntity.ok("The balance is: " + balance + ". Service charge: "
                    + TransactionCosts.CHECKING_BALANCE.getCost());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestParam("depositAmount") float depositAmount){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            String email = ((UserDetails) principal).getUsername();
            float  newBalance = transactionServices.Deposit(email, depositAmount);
            return ResponseEntity.ok("The new balance after deposit is: " + newBalance);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/send")
    public ResponseEntity<String> send(@RequestParam("receiverAccNumber") Integer receiverAccNumber,
                                       @RequestParam("amount") float sendAmount){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            String email = ((UserDetails) principal).getUsername();
            float newBalance = transactionServices.send(email, sendAmount, receiverAccNumber);
            return ResponseEntity.ok("You have successfully transferred " +sendAmount+ " to "+receiverAccNumber+
                    " The new balance after sending is: " + newBalance + ". Service fee: "
                    +TransactionCosts.SENDING_COST.getCost());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/statement")
    public ResponseEntity<List<Statement>> getStatement(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof  UserDetails){
            String email = ((UserDetails) principal).getUsername();
            User user  = statementService.findFirstByEmail(email);

            if(user != null){
                List<Statement> statement = statementService.getStatementByUser(user);
                return ResponseEntity.ok(statement);
            }
        }
        return ResponseEntity.notFound().build();
    }
}
