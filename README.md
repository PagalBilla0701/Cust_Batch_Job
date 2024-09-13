@ExtendWith(MockitoExtension.class)
public class CallReportServiceImplTest {

    @Mock
    IvrCallReportRepository ivrRepo;

    @Mock
    AcdCallReportRepository acdRepo;

    @Mock
    FullMenuTraversalRepository fullMenuTraversalRepo;

    @Mock
    FullMenuTraversal menuTraversal;

    @InjectMocks
    CallReportServiceImpl service;

    @Mock
    private IvrCallReportMapper ivrCallReportMapper;

    @Mock
    private AcdCallReportMapper acdCallReportMapper;

    @Test
    public void testIvrCallReportInsert() {
        IvrCallReportDto dto = new IvrCallReportDto();
        IvrCallReport report = new IvrCallReport();
        FullMenuTraversal menuTraversal = new FullMenuTraversal();

        when(ivrCallReportMapper.mapToIvrCallReportTable(dto)).thenReturn(report);
        when(ivrRepo.save(report)).thenReturn(report);
        when(fullMenuTraversalRepo.save(any(FullMenuTraversal.class))).thenReturn(menuTraversal);

        Boolean result = service.insertIvrCallReport(dto);
        assertThat(result).isTrue();
    }

    @Test
    public void testIvrCallReportInsert_negative() {
        IvrCallReportDto dto = new IvrCallReportDto();
        IvrCallReport report = new IvrCallReport();

        when(ivrCallReportMapper.mapToIvrCallReportTable(dto)).thenReturn(report);
        when(fullMenuTraversalRepo.save(any(FullMenuTraversal.class))).thenReturn(null);

        Boolean result = service.insertIvrCallReport(dto);
        assertThat(result).isFalse();
    }

    @Test
    public void testIvrCallReportInsert_withEmptyMenuTraversal() {
        IvrCallReportDto dto = new IvrCallReportDto();
        dto.setFullMenuTraversal(new HashMap<>()); // Set empty traversal
        IvrCallReport report = new IvrCallReport();

        when(ivrCallReportMapper.mapToIvrCallReportTable(dto)).thenReturn(report);
        when(ivrRepo.save(report)).thenReturn(report);
        when(fullMenuTraversalRepo.save(any(FullMenuTraversal.class))).thenReturn(menuTraversal);

        Boolean result = service.insertIvrCallReport(dto);
        assertThat(result).isTrue();
    }

    @Test
    public void testAcdCallReportInsert() {
        AcdCallReportDto dto = new AcdCallReportDto();
        AcdCallReport report = new AcdCallReport();

        when(acdCallReportMapper.mapToAcdCallReport(dto)).thenReturn(report);
        when(acdRepo.save(report)).thenReturn(report);

        Boolean result = service.insertAcdCallReport(dto);
        assertThat(result).isTrue();
    }

    @Test
    public void testAcdCallReportInsert_negative() {
        AcdCallReportDto dto = new AcdCallReportDto();
        AcdCallReport report = new AcdCallReport();

        when(acdCallReportMapper.mapToAcdCallReport(dto)).thenReturn(report);
        when(acdRepo.save(report)).thenReturn(null);

        Boolean result = service.insertAcdCallReport(dto);
        assertThat(result).isFalse();
    }

    @Test
    public void testSetMenusValue_AllMenusPresent() {
        // Arrange
        Map<String, String> menus = new HashMap<>();
        menus.put("Menu1", "Value1");
        menus.put("Menu2", "Value2");
        menus.put("Menu3", "Value3");
        menus.put("Menu4", "Value4");
        menus.put("Menu5", "Value5");
        menus.put("Menu6", "Value6");

        FullMenuTraversal menuTraversal = new FullMenuTraversal();

        // Act
        service.setMenusValue(menuTraversal, menus);

        // Assert
        assertEquals("Value1", menuTraversal.getMenu1());
        assertEquals("Value2", menuTraversal.getMenu2());
        assertEquals("Value3", menuTraversal.getMenu3());
        assertEquals("Value4", menuTraversal.getMenu4());
        assertEquals("Value5", menuTraversal.getMenu5());
        assertEquals("Value6", menuTraversal.getMenu6());
    }

    @Test
    public void testSetMenusValue_PartialMenusPresent() {
        // Arrange
        Map<String, String> menus = new HashMap<>();
        menus.put("Menu1", "Value1");
        menus.put("Menu2", "Value2");

        FullMenuTraversal menuTraversal = new FullMenuTraversal();

        // Act
        service.setMenusValue(menuTraversal, menus);

        // Assert
        assertEquals("Value1", menuTraversal.getMenu1());
        assertNull(menuTraversal.getMenu2());
        assertNull(menuTraversal.getMenu3());
        assertNull(menuTraversal.getMenu4());
        assertNull(menuTraversal.getMenu5());
        assertNull(menuTraversal.getMenu6());
    }

    @Test
    public void testSetMenusValue_NoMenusPresent() {
        // Arrange
        Map<String, String> menus = new HashMap<>();
        FullMenuTraversal menuTraversal = new FullMenuTraversal();

        // Act
        service.setMenusValue(menuTraversal, menus);

        // Assert
        assertNull(menuTraversal.getMenu1());
        assertNull(menuTraversal.getMenu2());
        assertNull(menuTraversal.getMenu3());
        assertNull(menuTraversal.getMenu4());
        assertNull(menuTraversal.getMenu5());
        assertNull(menuTraversal.getMenu6());
    }

    @Test
    public void testSetMenusValue_SomeMenusPresent() {
        // Arrange
        Map<String, String> menus = new HashMap<>();
        menus.put("Menu1", "Value1");

        FullMenuTraversal menuTraversal = new FullMenuTraversal();

        // Act
        service.setMenusValue(menuTraversal, menus);

        // Assert
        assertEquals("Value1", menuTraversal.getMenu1());
        assertNull(menuTraversal.getMenu2());
        assertNull(menuTraversal.getMenu3());
        assertNull(menuTraversal.getMenu4());
        assertNull(menuTraversal.getMenu5());
        assertNull(menuTraversal.getMenu6());
    }
}
