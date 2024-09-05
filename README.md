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

        when(feeWaiverService.findBynCardnum(cardNum)).thenReturn(feeWaiverDto);

        // When & Then
        mockMvc.perform(post("/feeWaiver/getFeeWaiver")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(feeWaiverDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.cardNum").value(cardNum));

        verify(feeWaiverService, times(1)).findBynCardnum(cardNum);
    }

    @Test
    void testGetFeeWaiver_NotFound() throws Exception {
        // Given
        String cardNum = "num12";

        when(feeWaiverService.findBynCardnum(cardNum)).thenReturn(null);

        // When & Then
        mockMvc.perform(post("/feeWaiver/getFeeWaiver")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"cardNum\":\"" + cardNum + "\"}"))
            .andExpect(status().isNotFound());

        verify(feeWaiverService, times(1)).findBynCardnum(cardNum);
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
            .header("country", "someCountry")
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
            .header("country", "someCountry")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(feeWaiverDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("Failure"));

        verify(feeWaiverService, times(1)).updateFeeWaiver(feeWaiverDto);
    }
}
