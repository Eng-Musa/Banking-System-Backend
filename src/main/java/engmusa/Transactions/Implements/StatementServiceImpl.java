package engmusa.Transactions.Implements;

import engmusa.Models.Statement;
import engmusa.Models.User;
import engmusa.Repository.StatementRepository;
import engmusa.Repository.UserRepository;
import engmusa.Transactions.Enum.TransactionType;
import engmusa.Transactions.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatementServiceImpl implements StatementService {
    @Autowired
    private StatementRepository statementRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public void saveStatement(Statement statement) {
        statementRepository.save(statement);
    }
    @Override
    public Statement generateStatement(User user, Float amount, Float balance, Float newBalance, TransactionType transactionType) {
        Statement statement = new Statement();
        statement.setTransactionDate(LocalDateTime.now());
        statement.setAmount(amount);
        statement.setBalance(balance);
        statement.setNewBalance(newBalance);
        statement.setUser(user);
        statement.setTransactionType(transactionType);
        return statement;
    }

    @Override
    public List<Statement> getStatementByUser(User user) {
        return statementRepository.findByUser(user);
    }

    @Override
    public User findFirstByEmail(String email) {
        return userRepository.findFirstByEmail(email);
    }

}
