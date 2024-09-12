import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
public class IvrApplicationTests {

    @Autowired
    private IvrApplication ivrApplication;

    @Test
    public void contextLoads() {
        assertNotNull(ivrApplication);
    }

    @Test
    public void testApplicationMain() {
        IvrApplication.main(new String[]{});
    }
}
