import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IVRCallActivityServiceImplTest {

    @Mock
    private IVRCallActivityRepository ivrCallActivityRepository;

    @InjectMocks
    private IVRCallActivityServiceImpl ivrCallActivityService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateIVRCallActivity_Success() {
        // Arrange
        Long refNo = 1L;
        String countryCode = "US";
        SecondFactorAuthentication secondFactorAuthentication = new SecondFactorAuthentication();
        secondFactorAuthentication.setSecondFactorAuthentication("OTP");

        IVRCallActivity ivrCallActivity = new IVRCallActivity();
        ivrCallActivity.setRefNo(refNo);
        ivrCallActivity.setCountryCode(countryCode);

        when(ivrCallActivityRepository.findByRefNoAndCountryCode(refNo, countryCode))
                .thenReturn(Optional.of(ivrCallActivity));

        // Act
        boolean result = ivrCallActivityService.updateIVRCallActivity(refNo, countryCode, secondFactorAuthentication);

        // Assert
        assertTrue(result);
        verify(ivrCallActivityRepository, times(1)).save(ivrCallActivity);
    }

    @Test
    public void testUpdateIVRCallActivity_RecordNotFound() {
        // Arrange
        Long refNo = 1L;
        String countryCode = "US";
        SecondFactorAuthentication secondFactorAuthentication = new SecondFactorAuthentication();
        secondFactorAuthentication.setSecondFactorAuthentication("OTP");

        when(ivrCallActivityRepository.findByRefNoAndCountryCode(refNo, countryCode))
                .thenReturn(Optional.empty());

        // Act
        boolean result = ivrCallActivityService.updateIVRCallActivity(refNo, countryCode, secondFactorAuthentication);

        // Assert
        assertFalse(result);
        verify(ivrCallActivityRepository, never()).save(any(IVRCallActivity.class));
    }
}
