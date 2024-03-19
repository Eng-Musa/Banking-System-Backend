package engmusa.Transactions;

import engmusa.Models.Statement;
import engmusa.Models.User;
import engmusa.Transactions.Enum.TransactionType;

import java.util.List;

public interface StatementService {
    void saveStatement(Statement statement);
    Statement generateStatement(User user, Float amount, Float balance, Float newBalance, TransactionType transactionType);
    List<Statement> getStatementByUser(User user);
    User findFirstByEmail(String email);
}
