Here’s the test class based on your provided structure, incorporating the existing test methods that work. I've created the remaining test cases for your `FeeWaiverController` based on the structure you shared:

```java
@ExtendWith(MockitoExtension.class)
public class FeeWaiverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FeeWaiverService feeWaiverService;

    @InjectMocks
    private FeeWaiverController feeWaiverController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(feeWaiverController).build();
    }

    @Test
    void testGetFeeWaiver_Success() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Given
        String cardNum = "num456";
        FeeWaiverDto feeWaiverDto = new FeeWaiverDto();
        feeWaiverDto.setCardNum(cardNum);
        feeWaiverDto.setAnnualFeeRequested("200");
        feeWaiverDto.setAnnualFeeRequestedDate(Calendar.getInstance().getTime());
        feeWaiverDto.setLateFeeRequested("N");
        feeWaiverDto.setAnnualFeeEligible("789");

        when(feeWaiverService.findBynCardnum(feeWaiverDto)).thenReturn(feeWaiverDto);

        // When & Then
        mockMvc.perform(post("/feeWaiver/getFeeWaiver")
            .header("country", "US")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(feeWaiverDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.cardNum").value(cardNum));

        verify(feeWaiverService, times(1)).findBynCardnum(feeWaiverDto);
    }

    @Test
    void testGetFeeWaiver_NotFound() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Given
        String cardNum = "num12";
        FeeWaiverDto feeWaiverDto = new FeeWaiverDto();
        feeWaiverDto.setCardNum(cardNum);

        when(feeWaiverService.findBynCardnum(feeWaiverDto)).thenReturn(null);

        // When & Then
        mockMvc.perform(post("/feeWaiver/getFeeWaiver")
            .header("country", "US")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(feeWaiverDto)))
            .andExpect(status().isNotFound());

        verify(feeWaiverService, times(1)).findBynCardnum(feeWaiverDto);
    }

    @Test
    void testUpdateFeeWaiver_Success() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Given
        String cardNum = "num456";
        FeeWaiverDto feeWaiverDto = new FeeWaiverDto();
        feeWaiverDto.setCardNum(cardNum);
        feeWaiverDto.setAnnualFeeRequested("200");
        feeWaiverDto.setAnnualFeeRequestedDate(Calendar.getInstance().getTime());
        feeWaiverDto.setLateFeeRequested("N");
        feeWaiverDto.setAnnualFeeEligible("789");

        when(feeWaiverService.updateFeeWaiver(feeWaiverDto)).thenReturn(true);

        // When & Then
        mockMvc.perform(patch("/feeWaiver/updateFeeWaiver")
            .header("country", "US")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(feeWaiverDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("Success"));

        verify(feeWaiverService, times(1)).updateFeeWaiver(feeWaiverDto);
    }

    @Test
    void testUpdateFeeWaiver_Failure() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Given
        String cardNum = "num456";
        FeeWaiverDto feeWaiverDto = new FeeWaiverDto();
        feeWaiverDto.setCardNum(cardNum);
        feeWaiverDto.setAnnualFeeRequested("200");
        feeWaiverDto.setAnnualFeeRequestedDate(Calendar.getInstance().getTime());
        feeWaiverDto.setLateFeeRequested("N");
        feeWaiverDto.setAnnualFeeEligible("789");

        when(feeWaiverService.updateFeeWaiver(feeWaiverDto)).thenReturn(false);

        // When & Then
        mockMvc.perform(patch("/feeWaiver/updateFeeWaiver")
            .header("country", "US")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(feeWaiverDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("Failure"));

        verify(feeWaiverService, times(1)).updateFeeWaiver(feeWaiverDto);
    }
}
```

### Key Points:
1. **Test Setup:** The `MockMvc` is properly initialized before each test using `MockMvcBuilders.standaloneSetup(feeWaiverController).build()`.
   
2. **Test Methods:**
   - `testGetFeeWaiver_Success()`: Tests the successful retrieval of a fee waiver.
   - `testGetFeeWaiver_NotFound()`: Tests the scenario where no fee waiver is found, and a `404 Not Found` status is returned.
   - `testUpdateFeeWaiver_Success()`: Tests the successful update of a fee waiver, returning a status of "Success".
   - `testUpdateFeeWaiver_Failure()`: Tests the failed update of a fee waiver, returning a status of "Failure".

3. **Mocking:** We use `Mockito` to mock the `feeWaiverService` behavior to return the desired objects or null values for each test scenario.

You can use these methods to test the functionality of your `FeeWaiverController`.
