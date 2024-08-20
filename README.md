Here's a refactored version of the `IvrReportsControllerTest` class, aligned with the provided `IvrReportsController`:

### Refactored `IvrReportsControllerTest`

```java
package com.sc.rdc.cops.ivrapi.controller;

import com.sc.rdc.cops.ivrapi.dto.AcdCallReportDto;
import com.sc.rdc.cops.ivrapi.dto.IvrCallReportDto;
import com.sc.rdc.cops.ivrapi.dto.StatusDto;
import com.sc.rdc.cops.ivrapi.service.CallReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IvrReportsControllerTest {

    @InjectMocks
    private IvrReportsController controller;

    @Mock
    private CallReportService service;

    @Test
    public void testIvrCallReport_Positive() {
        IvrCallReportDto ivrDto = new IvrCallReportDto();
        ResponseEntity<Object> result;

        when(service.insertIvrCallReport(ivrDto)).thenReturn(true);

        result = controller.insertIvrCallReport(ivrDto, "SG");

        assertThat(result).isNotNull();
        StatusDto dto = (StatusDto) result.getBody();
        assertThat(dto.getStatus()).isEqualTo("Success");
    }

    @Test
    public void testIvrCallReport_Negative() {
        IvrCallReportDto ivrDto = new IvrCallReportDto();
        ResponseEntity<Object> result;

        when(service.insertIvrCallReport(ivrDto)).thenReturn(false);

        result = controller.insertIvrCallReport(ivrDto, "SG");

        assertThat(result).isNotNull();
        StatusDto dto = (StatusDto) result.getBody();
        assertThat(dto.getStatus()).isEqualTo("Failure");
    }

    @Test
    public void testAcdCallReport_Positive() {
        AcdCallReportDto acdDto = new AcdCallReportDto();
        ResponseEntity<Object> result;

        when(service.insertAcdCallReport(acdDto)).thenReturn(true);

        result = controller.insertAcdCallReport(acdDto, "SG");

        assertThat(result).isNotNull();
        StatusDto dto = (StatusDto) result.getBody();
        assertThat(dto.getStatus()).isEqualTo("Success");
    }

    @Test
    public void testAcdCallReport_Negative() {
        AcdCallReportDto acdDto = new AcdCallReportDto();
        ResponseEntity<Object> result;

        when(service.insertAcdCallReport(acdDto)).thenReturn(false);

        result = controller.insertAcdCallReport(acdDto, "SG");

        assertThat(result).isNotNull();
        StatusDto dto = (StatusDto) result.getBody();
        assertThat(dto.getStatus()).isEqualTo("Failure");
    }
}
```

### Summary of Changes
1. **Import Statements**: Added necessary import statements for the DTOs, `StatusDto`, and `CallReportService`.
2. **Controller Field**: Changed `controller` and `service` fields to `private` for encapsulation.
3. **Corrected Method Calls**: Updated `insertIvrCallReport` and `insertAcdCallReport` methods to properly align with the `controller` class signature.
4. **Removed Redundant Statements**: Removed redundant lines to ensure a clear and concise test.

This refactor brings your test class in line with the structure and functionality of the provided controller class.
