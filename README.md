To achieve what you're describing, you want to:

Fetch the CSV file content stored in the CemsAuditLogEntity table, specifically from the X_FILE_NAME and B_DATA_FILE_CONTENT columns, by querying the database.
Write the content of the file to a specific location on your filesystem, such as C:\\Users\\user\\Desktop\\CsvReader.
Here’s a step-by-step breakdown of how you can implement this:

1. Create a Service to Fetch the File from the Database
This service will query the CemsAuditLogEntity table by ID to fetch the necessary file information and content.

java
Copy code
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.io.FileUtils;

@Service
public class CemsAuditLogService {

    @Autowired
    private CemsAuditLogRepository auditLogRepository;

    @Transactional
    public File fetchAndWriteFile(BigDecimal id, String destinationDirectory) throws IOException {
        // Fetch the file entity by its ID
        Optional<CemsAuditLogEntity> optionalFile = auditLogRepository.findById(id);

        if (optionalFile.isPresent()) {
            CemsAuditLogEntity fileEntity = optionalFile.get();
            
            // Retrieve the file name and content
            String fileName = fileEntity.getFileName();
            byte[] fileContent = fileEntity.getDataFileContent(); // Assuming this holds the file content

            // Define the file path
            File file = new File(destinationDirectory + File.separator + fileName);

            // Write file to disk
            FileUtils.writeByteArrayToFile(file, fileContent);

            return file;  // Return the file object if needed elsewhere
        } else {
            throw new IOException("File not found in the database for ID: " + id);
        }
    }
}
2. CemsAuditLogEntity
You should add the necessary annotations and the byte array field to store the file content (assuming it's stored as a binary blob in B_DATA_FILE_CONTENT).

java
Copy code
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "CEMS_LMS_FILE_AUDIT")
public class CemsAuditLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_FILE")
    private BigDecimal fileId;

    @Column(name = "X_FILE_NAME")
    private String fileName;

    @Column(name = "B_DATA_FILE_CONTENT")
    @Lob
    private byte[] dataFileContent; // Store the file content as a byte array

    // Getters and setters...

    public BigDecimal getFileId() {
        return fileId;
    }

    public void setFileId(BigDecimal fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getDataFileContent() {
        return dataFileContent;
    }

    public void setDataFileContent(byte[] dataFileContent) {
        this.dataFileContent = dataFileContent;
    }
}
3. Repository Layer
You’ll need a repository to interact with the CemsAuditLogEntity table and fetch records by ID.

java
Copy code
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigDecimal;

public interface CemsAuditLogRepository extends JpaRepository<CemsAuditLogEntity, BigDecimal> {
}
4. Integrate into Reader Class
Finally, in your existing reader class, instead of reading directly from the file system, you can first fetch the file from the database, write it to the specified location (C:\\Users\\user\\Desktop\\CsvReader), and then proceed with reading that file using your FlatFileItemReader.

java
Copy code
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.batch.item.file.LineMapper;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

@Configuration
public class KycIndicatorsReader {

    @Autowired
    private CemsAuditLogService auditLogService;

    @Bean
    public FlatFileItemReader<KycIndicatorDTO> kycFileReader() throws IOException {
        // Fetch the file from the database and save it to the local directory
        BigDecimal fileId = new BigDecimal(1); // Replace with the actual file ID
        File csvFile = auditLogService.fetchAndWriteFile(fileId, "C:/Users/user/Desktop/CsvReader");

        FlatFileItemReader<KycIndicatorDTO> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource(csvFile));
        itemReader.setName("kycFileReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    public LineMapper<KycIndicatorDTO> lineMapper() {
        DefaultLineMapper<KycIndicatorDTO> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("relId", "fKycStatus");

        BeanWrapperFieldSetMapper<KycIndicatorDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(KycIndicatorDTO.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }
}
Explanation:
CemsAuditLogService: This service fetches the file from the database using the repository and writes the content to the specified file location.
CemsAuditLogEntity: The entity is mapped to the database table with fileName and dataFileContent fields.
CemsAuditLogRepository: The repository handles database interactions to fetch the file data.
KycIndicatorsReader: The reader fetches the file from the database using the service, writes it to the file system, and reads it using FlatFileItemReader.
This approach ensures that the CSV file is first fetched from the database and saved locally before being processed.
