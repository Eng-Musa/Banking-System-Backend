package engmusa.Transactions;

import engmusa.Models.User;

import java.util.concurrent.atomic.AtomicInteger;

public interface TransactionServices {
    Float getBalance(String email);
    Float Deposit(String email, float depositAmount);
    Float send(String email, float sendAmount, Integer receiverAccNumber);
}
