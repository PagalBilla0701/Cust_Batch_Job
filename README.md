   To write a test class for the `IVRCallActivityController` using JUnit and Mockito, you can follow the below example. This test class will focus on testing the `updateCallActivity` method of the controller.

Here's how you can write the test class:

```java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IVRCallActivityControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IVRCallActivityService ivrCallActivityService;

    @InjectMocks
    private IVRCallActivityController ivrCallActivityController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ivrCallActivityController).build();
    }

    @Test
    public void testUpdateCallActivity_Success() throws Exception {
        Long refNo = 12345L;
        String countryCode = "US";
        SecondFactorAuthentication secondFactorAuthentication = new SecondFactorAuthentication();
        
        // Mock the service method
        when(ivrCallActivityService.updateIVRCallActivity(refNo, countryCode, secondFactorAuthentication))
                .thenReturn(true);

        // Call the controller method
        ResponseEntity<StatusDto> response = ivrCallActivityController.updateCallActivity(refNo, countryCode, secondFactorAuthentication);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", response.getBody().getStatus());
    }

    @Test
    public void testUpdateCallActivity_Failure() throws Exception {
        Long refNo = 12345L;
        String countryCode = "US";
        SecondFactorAuthentication secondFactorAuthentication = new SecondFactorAuthentication();
        
        // Mock the service method
        when(ivrCallActivityService.updateIVRCallActivity(refNo, countryCode, secondFactorAuthentication))
                .thenReturn(false);

        // Call the controller method
        ResponseEntity<StatusDto> response = ivrCallActivityController.updateCallActivity(refNo, countryCode, secondFactorAuthentication);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Failure", response.getBody().getStatus());
    }
}
```

### Explanation:
1. **`@Mock`**: We mock the `IVRCallActivityService` to simulate the service layer behavior without actually invoking it.
2. **`@InjectMocks`**: This annotation is used to inject the mocked dependencies into the `IVRCallActivityController` instance.
3. **`MockMvc`**: This is used to test the controller layer without starting the server. We set it up in the `setUp()` method.
4. **Test Methods**:
   - `testUpdateCallActivity_Success()`: This tests the scenario where the service returns `true`, simulating a successful update.
   - `testUpdateCallActivity_Failure()`: This tests the scenario where the service returns `false`, simulating a failure in the update.

These tests ensure that the `updateCallActivity` method behaves as expected when interacting with the `IVRCallActivityService`.
