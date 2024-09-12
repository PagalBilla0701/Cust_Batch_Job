import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

public class MapToJsonConverterTest {

    private MapToJsonConverter converter;

    @BeforeEach
    public void setUp() {
        converter = new MapToJsonConverter();
    }

    @Test
    public void testConvertToDatabaseColumn() {
        // Given
        Map<String, String> map = new HashMap<>();
        map.put("Menu1", "START");
        map.put("Menu2", "BIRTH_GRT");

        // When
        String json = converter.convertToDatabaseColumn(map);

        // Then
        assertNotNull(json);
        assertTrue(json.contains("\"Menu1\":\"START\""));
        assertTrue(json.contains("\"Menu2\":\"BIRTH_GRT\""));
    }

    @Test
    public void testConvertToDatabaseColumnNull() {
        // Given
        Map<String, String> map = null;

        // When
        String json = converter.convertToDatabaseColumn(map);

        // Then
        assertNull(json);  // null should remain null
    }

    @Test
    public void testConvertToEntityAttribute() {
        // Given
        String json = "{\"Menu1\":\"START\",\"Menu2\":\"BIRTH_GRT\"}";

        // When
        Map<String, String> map = converter.convertToEntityAttribute(json);

        // Then
        assertNotNull(map);
        assertEquals("START", map.get("Menu1"));
        assertEquals("BIRTH_GRT", map.get("Menu2"));
    }

    @Test
    public void testConvertToEntityAttributeNull() {
        // Given
        String json = null;

        // When
        Map<String, String> map = converter.convertToEntityAttribute(json);

        // Then
        assertNull(map);  // null should remain null
    }

    @Test
    public void testConvertToEntityAttributeInvalidJson() {
        // Given
        String invalidJson = "{invalid json}";

        // Then
        assertThrows(IllegalArgumentException.class, () -> {
            // When
            converter.convertToEntityAttribute(invalidJson);
        });
    }
}
