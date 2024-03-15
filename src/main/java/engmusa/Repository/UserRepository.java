package engmusa.Repository;

import engmusa.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.concurrent.atomic.AtomicInteger;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByEmail(String email);
}
