package engmusa.Transactions;

import engmusa.Models.User;

import java.util.concurrent.atomic.AtomicInteger;

public interface TransactionServices {
    Float getBalance(String email);
}
