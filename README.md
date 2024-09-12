import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class) // JUnit 5 Extension for Spring Support
@DataJpaTest // This enables in-memory DB testing with JPA
public class IvrCallReportTest {

    @Autowired
    private IvrCallReportRepository ivrCallReportRepository; // Assuming you have a repository

    @PersistenceContext
    private EntityManager entityManager;

    private IvrCallReport ivrCallReport;

    @BeforeEach
    void setUp() {
        // Create a sample IvrCallReport instance with a map
        Map<String, String> fullMenuTraversal = new HashMap<>();
        fullMenuTraversal.put("Menu1", "START");
        fullMenuTraversal.put("Menu2", "BIRTH_GRT");

        ivrCallReport = new IvrCallReport();
        ivrCallReport.setCli(63949242781L);
        ivrCallReport.setUcid(107654678L);
        ivrCallReport.setFullMenuTraversal(fullMenuTraversal);
        ivrCallReport.setXCreat("TestCreator");
        ivrCallReport.setDCreat(new Date());
        ivrCallReport.setXUpd("TestUpdater");
        ivrCallReport.setDUpd(new Date());
    }

    @Test
    @Transactional
    public void testSaveAndRetrieveIvrCallReport() throws JsonProcessingException {
        // Save the IvrCallReport entity
        IvrCallReport savedReport = ivrCallReportRepository.save(ivrCallReport);

        // Clear persistence context to force reload from DB
        entityManager.clear();

        // Retrieve the entity from the database
        IvrCallReport retrievedReport = ivrCallReportRepository.findById(savedReport.getId()).orElse(null);

        // Assert that the retrieved entity is not null
        assertThat(retrievedReport).isNotNull();

        // Assert the properties of the retrieved entity
        assertThat(retrievedReport.getCli()).isEqualTo(ivrCallReport.getCli());
        assertThat(retrievedReport.getUcid()).isEqualTo(ivrCallReport.getUcid());
        assertThat(retrievedReport.getXCreat()).isEqualTo(ivrCallReport.getXCreat());

        // Test the Map<String, String> conversion and retrieval
        assertThat(retrievedReport.getFullMenuTraversal()).isNotNull();
        assertThat(retrievedReport.getFullMenuTraversal().get("Menu1")).isEqualTo("START");
        assertThat(retrievedReport.getFullMenuTraversal().get("Menu2")).isEqualTo("BIRTH_GRT");
    }

    @Test
    @Transactional
    public void testEmptyFullMenuTraversal() {
        // Set an empty map for fullMenuTraversal
        ivrCallReport.setFullMenuTraversal(new HashMap<>());

        // Save and retrieve the entity
        IvrCallReport savedReport = ivrCallReportRepository.save(ivrCallReport);
        entityManager.clear(); // Clear the persistence context
        IvrCallReport retrievedReport = ivrCallReportRepository.findById(savedReport.getId()).orElse(null);

        // Assert that the map is empty
        assertThat(retrievedReport).isNotNull();
        assertThat(retrievedReport.getFullMenuTraversal()).isEmpty();
    }
}
