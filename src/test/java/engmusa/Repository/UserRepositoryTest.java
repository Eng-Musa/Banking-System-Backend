package engmusa.Repository;

import engmusa.Models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;
    @Test
    void itShouldFindFirstByEmail() {
        //given
        String email = "musa@gmail.com";
        User user = new User();
        user.setEmail(email);
        underTest.save(user);

        //when
        User exists = underTest.findFirstByEmail(email);

        //then
        assertThat(exists).isNotNull();
        assertThat(exists.getEmail()).isEqualTo(email);

    }
}
