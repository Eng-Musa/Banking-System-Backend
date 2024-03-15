package engmusa.Transactions;

import engmusa.Models.User;
import engmusa.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;
@Service
public class TransactionServicesImp implements TransactionServices{
    @Autowired
    private UserRepository userRepository;

    @Override
    public Float getBalance(String email) {
        User fetchedUser = userRepository.findFirstByEmail(email);
        if(fetchedUser != null){
            return fetchedUser.getAccountBalance();
        }else {
            throw new RuntimeException("Invalid user");
        }
    }
    @Override
    public Float Deposit(String email, float depositAmount) {
        User fetchedUser = userRepository.findFirstByEmail(email);
        if(fetchedUser != null){
            float balance = fetchedUser.getAccountBalance();
            float newBalance = balance +depositAmount;
            fetchedUser.setAccountBalance(newBalance);
            userRepository.save(fetchedUser);
            return newBalance;
        }else{
            throw new RuntimeException("Invalid User");
        }
    }
}
