import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import com.place2book.internal.model.Organizer;
import com.place2book.internal.repository.P2BRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

@SpringBootTest
public class P2BRepositoryIT {
    // private final JdbcClient p2b;
    
    // P2BRepositoryIT(@Qualifier("place2book")JdbcClient p2b) {
    //     this.p2b = p2b;
    // }
    
    private final P2BRepository p2brepo;
    
    @Autowired
    P2BRepositoryIT(P2BRepository p2brepo) {
        this.p2brepo = p2brepo;
    }
    
    @Test
    void findOrganizerById() {
        Organizer organizer = p2brepo.findOrganizerById(1).orElseThrow();
        assertThat(organizer.getName()).isEqualTo("Aalborg Jazz Festival");
    }
}
