@ExtendWith(MockitoExtension.class)
class FeeWaiverServiceImplMYTest {

    @Mock
    private FeeWaiverRepositoryMY mockFeeWaiverRepository;

    @InjectMocks
    private FeeWaiverServiceImplMY feeWaiverServiceImplMYUnderTest;

    private FeeWaiverDto dto;

    @BeforeEach
    void setUp() {
        dto = new FeeWaiverDto();
        dto.setCardNum("cardNum");
        dto.setAnnualFeeRequested("annualFeeRequested");
        dto.setAnnualFeeRequestedDate(LocalDate.now());
        dto.setLateFeeRequested("lateFeeRequested");
        dto.setLateFeeRequestedDate(LocalDate.now());
        dto.setAnnualFeeEligible("annualFeeEligible");
        dto.setLateFeeEligible("lateFeeEligible");
    }

    @Test
    void testGetCountryCode() {
        assertThat(feeWaiverServiceImplMYUnderTest.getCountryCode()).isEqualTo("MY");
    }

    @Test
    void testFindBynCardnum() {
        // Setup the expected result
        FeeWaiverDto expectedResult = new FeeWaiverDto();
        expectedResult.setAnnualFeeEligible("annualFeeEligible");
        expectedResult.setCardNum("cardNum");
        expectedResult.setAnnualFeeRequested("annualFeeRequested");
        expectedResult.setLateFeeRequested("lateFeeRequested");
        expectedResult.setLateFeeRequestedDate(LocalDate.now());
        expectedResult.setAnnualFeeRequestedDate(LocalDate.now());
        expectedResult.setLateFeeEligible("lateFeeEligible");

        // Configure FeeWaiverRepositoryMY.findById(...)
        FeeWaiverMY feeWaiverMY = new FeeWaiverMY();
        feeWaiverMY.setNCardnum("cardNum");
        feeWaiverMY.setFLateFeeEligible("lateFeeEligible");
        feeWaiverMY.setNAnnualFeeRequested("annualFeeRequested");
        feeWaiverMY.setDAnnualFeeReqDate(LocalDate.now());
        feeWaiverMY.setFLateFeeRequested("lateFeeRequested");
        feeWaiverMY.setDLateFeeReqDate(LocalDate.now());
        feeWaiverMY.setNAnnualFeeEligible("annualFeeEligible");
        when(mockFeeWaiverRepository.findById("cardNum")).thenReturn(Optional.of(feeWaiverMY));

        // Run the test
        FeeWaiverDto result = feeWaiverServiceImplMYUnderTest.findBynCardnum(dto);

        // Verify the results
        assertThat(result).isEqualToComparingFieldByField(expectedResult);
    }

    @Test
    void testFindBynCardnum_FeeWaiverRepositoryMYReturnsAbsent() {
        // Setup
        when(mockFeeWaiverRepository.findById("cardNum")).thenReturn(Optional.empty());

        // Run the test
        FeeWaiverDto result = feeWaiverServiceImplMYUnderTest.findBynCardnum(dto);

        // Verify the results
        assertThat(result).isNull();
    }

    @Test
    void testUpdateFeeWaiver() {
        // Setup
        FeeWaiverMY feeWaiverMY = new FeeWaiverMY();
        feeWaiverMY.setNCardnum("cardNum");
        feeWaiverMY.setFLateFeeEligible("lateFeeEligible");
        feeWaiverMY.setNAnnualFeeRequested("annualFeeRequested");
        feeWaiverMY.setDAnnualFeeReqDate(LocalDate.now());
        feeWaiverMY.setFLateFeeRequested("lateFeeRequested");
        feeWaiverMY.setDLateFeeReqDate(LocalDate.now());
        feeWaiverMY.setNAnnualFeeEligible("annualFeeEligible");

        when(mockFeeWaiverRepository.findById("cardNum")).thenReturn(Optional.of(feeWaiverMY));
        when(mockFeeWaiverRepository.save(any(FeeWaiverMY.class))).thenReturn(feeWaiverMY);

        // Run the test
        Boolean result = feeWaiverServiceImplMYUnderTest.updateFeeWaiver(dto);

        // Verify the results
        assertThat(result).isTrue();
        verify(mockFeeWaiverRepository).findById("cardNum");
        verify(mockFeeWaiverRepository).save(any(FeeWaiverMY.class));
    }

    @Test
    void testUpdateFeeWaiver_FeeWaiverRepositoryMYReturnsAbsent() {
        // Setup
        when(mockFeeWaiverRepository.findById("cardNum")).thenReturn(Optional.empty());

        // Run the test
        Boolean result = feeWaiverServiceImplMYUnderTest.updateFeeWaiver(dto);

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    void testUpdateFeeWaiver_FeeWaiverRepositoryMYSaveThrowsOptimisticLockingFailureException() {
        // Setup
        FeeWaiverMY feeWaiverMY = new FeeWaiverMY();
        feeWaiverMY.setNCardnum("cardNum");
        when(mockFeeWaiverRepository.findById("cardNum")).thenReturn(Optional.of(feeWaiverMY));
        when(mockFeeWaiverRepository.save(any(FeeWaiverMY.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test and verify exception
        assertThatThrownBy(() -> feeWaiverServiceImplMYUnderTest.updateFeeWaiver(dto))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testFindBynCardnum_WithInvalidCardNum() {
        // Setup: test for invalid card number
        dto.setCardNum(""); // empty card number
        when(mockFeeWaiverRepository.findById("")).thenReturn(Optional.empty());

        // Run the test
        FeeWaiverDto result = feeWaiverServiceImplMYUnderTest.findBynCardnum(dto);

        // Verify the result
        assertThat(result).isNull();
    }
}
