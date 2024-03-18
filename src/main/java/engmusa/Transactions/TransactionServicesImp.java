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
    @Override
    public Float send(String email, float sendAmount, Integer receiverAccNumber) {
        User sendingUser = userRepository.findFirstByEmail(email);
        if(sendingUser != null){
            float balance = sendingUser.getAccountBalance();
            float newBalance = balance - sendAmount;
            sendingUser.setAccountBalance(newBalance);
            userRepository.save(sendingUser);

            AtomicInteger atomicAccNumber = new AtomicInteger(receiverAccNumber);

            User receivingUser = userRepository.findFirstByAccountNumber(atomicAccNumber);
            if(receivingUser != null){
                float balance1 = receivingUser.getAccountBalance();
                float newBalance1 = balance1 + sendAmount;
                receivingUser.setAccountBalance(newBalance1);
                userRepository.save(receivingUser);
                return newBalance;
            }else{
                throw new RuntimeException("Invalid receiver account number");
            }
        }else{
            throw new RuntimeException("Invalid user");
        }
    }
}