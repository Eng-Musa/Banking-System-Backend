package engmusa.Transactions;

import engmusa.Models.User;

public interface TransactionServices {
    Float getBalance(String email);
    Float Deposit(String email, float depositAmount);
    Float send(String email, float sendAmount, Integer receiverAccNumber);
}
