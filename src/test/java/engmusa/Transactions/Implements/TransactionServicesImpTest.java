package engmusa.Transactions.Implements;

import engmusa.Models.User;
import engmusa.Repository.UserRepository;
import engmusa.Transactions.Enum.TransactionType;
import engmusa.Transactions.StatementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TransactionServicesImpTest {

    @InjectMocks
    TransactionServicesImp transactionServicesImp;

    @Mock
    UserRepository userRepository;

    @Mock
    StatementService statementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getBalance_ValidUser_ReturnsBalance() {
        // Given
        String email = "test@example.com";
        User user = new User();
        user.setAccountBalance(1000f);
        when(userRepository.findFirstByEmail(email)).thenReturn(user);

        // When
        Float balance = transactionServicesImp.getBalance(email);

        // Then
        assertEquals(1000f, balance);
        verify(userRepository, times(1)).findFirstByEmail(email);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deposit_ValidUser_DepositsAmount() {
        // Given
        String email = "test@example.com";
        User user = new User();
        user.setAccountBalance(1000f);
        when(userRepository.findFirstByEmail(email)).thenReturn(user);

        // When
        Float newBalance = transactionServicesImp.Deposit(email, 500f);

        // Then
        assertEquals(1500f, newBalance);
        verify(userRepository, times(1)).findFirstByEmail(email);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void send_ValidUserAndReceiver_TransfersAmount() {
        // Given
        String email = "test@example.com";
        Integer receiverAccNumber = 123456;
        User sendingUser = new User();
        sendingUser.setAccountBalance(1000f);
        User receivingUser = new User();
        receivingUser.setAccountBalance(500f);
        when(userRepository.findFirstByEmail(email)).thenReturn(sendingUser);
        when(userRepository.findFirstByAccountNumber(receiverAccNumber)).thenReturn(receivingUser);

        // When
        Float newBalance = transactionServicesImp.send(email, 200f, receiverAccNumber);

        // Then
        assertEquals(775f, newBalance); // 1000 - 200 - 2 (sending cost)
        verify(userRepository, times(1)).findFirstByEmail(email);
        verify(userRepository, times(1)).findFirstByAccountNumber(receiverAccNumber);
        verify(userRepository, times(2)).save(any(User.class));
        verify(statementService, times(2)).generateStatement(any(), anyFloat(), anyFloat(), anyFloat(), any(TransactionType.class));
        verify(statementService, times(2)).saveStatement(any());
    }
    @Test
    void send_InsufficientFunds_ThrowsException() {
        // Given
        String email = "test@example.com";
        Integer receiverAccNumber = 123456;
        User sendingUser = new User();
        sendingUser.setAccountBalance(100f);
        when(userRepository.findFirstByEmail(email)).thenReturn(sendingUser);

        // When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            transactionServicesImp.send(email, 200f, receiverAccNumber);
        });

        // Then
        assertEquals("Insufficient funds to complete the transaction", exception.getMessage());
    }

    @Test
    void send_InvalidReceiver_ThrowsException() {
        // Given
        String email = "test@example.com";
        Integer receiverAccNumber = 123456;
        User sendingUser = new User();
        sendingUser.setAccountBalance(1000f);
        when(userRepository.findFirstByEmail(email)).thenReturn(sendingUser);
        when(userRepository.findFirstByAccountNumber(receiverAccNumber)).thenReturn(null);

        // When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            transactionServicesImp.send(email, 200f, receiverAccNumber);
        });

        // Then
        assertEquals("Invalid receiver account number", exception.getMessage());
    }
}
