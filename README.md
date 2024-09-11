The error you're seeing indicates that Hibernate (or the ORM you're using) cannot determine how to map a Map<String, String> directly into a database column. Hibernate, by default, doesn't know how to persist complex types like Map<String, String> without explicit instructions.

To resolve this issue and allow the dynamic storage of the fullMenuTraversal field (which is a Map<String, String>), you can:

Option 1: Serialize the Map as a JSON String

This is one of the most common approaches. You can store the Map<String, String> as a JSON string in a database column, and deserialize it when retrieving data. For this, you can use @Convert with a custom AttributeConverter to handle the conversion between the Map<String, String> and String.

Here’s how to implement it:

1. Create a Converter for the Map<String, String>

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Converter(autoApply = true)
public class MapToJsonConverter implements AttributeConverter<Map<String, String>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, String> attribute) {
        try {
            // Convert Map to JSON String
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting map to JSON", e);
        }
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String dbData) {
        try {
            // Convert JSON String to Map
            return objectMapper.readValue(dbData, HashMap.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading JSON from database", e);
        }
    }
}

2. Update Your IvrCallReport Entity to Use the Converter

import javax.persistence.Convert;
import java.util.Map;

@Entity
public class IvrCallReport {

    // Other fields...

    @Convert(converter = MapToJsonConverter.class)
    private Map<String, String> fullMenuTraversal;

    // Getters and setters
    public Map<String, String> getFullMenuTraversal() {
        return fullMenuTraversal;
    }

    public void setFullMenuTraversal(Map<String, String> fullMenuTraversal) {
        this.fullMenuTraversal = fullMenuTraversal;
    }
}

3. Adjust Your DTO and Service Logic

In your IvrCallReportDto, ensure you have a Map<String, String> for fullMenuTraversal:

public class IvrCallReportDto {
    private Map<String, String> fullMenuTraversal;

    // Getters and setters
    public Map<String, String> getFullMenuTraversal() {
        return fullMenuTraversal;
    }

    public void setFullMenuTraversal(Map<String, String> fullMenuTraversal) {
        this.fullMenuTraversal = fullMenuTraversal;
    }
}

In your CallReportService implementation, you should already have logic to populate this map and save the entity:

@Override
public Boolean insertIvrCallReport(IvrCallReportDto dto) {
    Boolean inserted = false;

    // Create an instance of IvrCallReport
    IvrCallReport report = new IvrCallReport();

    // Set creation and update details
    Date currentDate = Calendar.getInstance().getTime();
    report.setXCreat("IVR");
    report.setDCreat(currentDate);
    report.setDUpd(currentDate);
    report.setXUpd("IVR");

    // Set the fullMenuTraversal map
    report.setFullMenuTraversal(dto.getFullMenuTraversal());

    // Save the report and check if it was saved successfully
    IvrCallReport savedReport = ivrRepo.save(report);
    if (Objects.nonNull(savedReport)) {
        inserted = true;
    }

    return inserted;
}

Option 2: Create a Separate Table for fullMenuTraversal

If you want to model this as a separate table and store each key-value pair from the Map<String, String> into the database, you will need to create a @OneToMany relationship between the IvrCallReport entity and another entity representing the menu traversal.

Here’s a quick overview of this approach:

1. Create a FullMenuTraversal Entity:



@Entity
public class FullMenuTraversal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String menuKey;

    private String menuValue;

    @ManyToOne
    @JoinColumn(name = "ivr_call_report_id")
    private IvrCallReport ivrCallReport;

    // Getters and setters
}

2. Update IvrCallReport Entity:



@Entity
public class IvrCallReport {

    // Other fields...

    @OneToMany(mappedBy = "ivrCallReport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FullMenuTraversal> fullMenuTraversals = new ArrayList<>();

    // Getters and setters
}

3. Adjust Logic to Populate the Relationship:



In your service, iterate through the Map<String, String> and populate the FullMenuTraversal entity list:

@Override
public Boolean insertIvrCallReport(IvrCallReportDto dto) {
    Boolean inserted = false;

    IvrCallReport report = new IvrCallReport();
    Date currentDate = Calendar.getInstance().getTime();
    report.setXCreat("IVR");
    report.setDCreat(currentDate);
    report.setDUpd(currentDate);
    report.setXUpd("IVR");

    // Populate FullMenuTraversal entities
    List<FullMenuTraversal> traversalList = new ArrayList<>();
    for (Map.Entry<String, String> entry : dto.getFullMenuTraversal().entrySet()) {
        FullMenuTraversal traversal = new FullMenuTraversal();
        traversal.setMenuKey(entry.getKey());
        traversal.setMenuValue(entry.getValue());
        traversal.setIvrCallReport(report);
        traversalList.add(traversal);
    }
    report.setFullMenuTraversals(traversalList);

    IvrCallReport savedReport = ivrRepo.save(report);
    if (Objects.nonNull(savedReport)) {
        inserted = true;
    }

    return inserted;
}

Conclusion:

Both options are valid, and the choice depends on your requirements. If fullMenuTraversal is relatively simple and you don't need to query it individually, the JSON serialization approach (Option 1) is easier. If you need to query or manipulate the fullMenuTraversal data independently, Option 2 (with a separate table) would be better.

