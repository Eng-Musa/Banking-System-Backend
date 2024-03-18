package engmusa.Repository;

import engmusa.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByEmail(String email);
    User findFirstByAccountNumber(Integer accountNumber);
    @Query("SELECT MAX(u.accountNumber) FROM User u")
    Integer findMaxAccountNumber();
}
