package kr.re.kh.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class UploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileTarget;

    private String fileName;

    private String saveFileName;

    @JsonIgnore
    private String filePath;

    @JsonIgnore
    private String fileDir;

    private String contentType;

    private long fileSize;

    private String username;

    private String fileUrl;   // 클라이언트가 접근할 수 있는 URL

    @JsonIgnore
    private LocalDateTime createdAt;

    public UploadFile(String fileName, String saveFileName, String filePath, String contentType, String fileUrl, long fileSize, String fileDir, String fileTarget, String username) {
        this.fileName = fileName;
        this.saveFileName = saveFileName;
        this.filePath = filePath;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.fileDir = fileDir;
        this.fileTarget = fileTarget;
        this.username = username;
        this.fileUrl = fileUrl;

    }

    @Builder
    public UploadFile(String fileTarget, String fileName, String saveFileName, String filePath, String fileDir, String contentType, String fileUrl, long fileSize, LocalDateTime createdAt, String username) {
        this.fileTarget = fileTarget;
        this.fileName = fileName;
        this.saveFileName = saveFileName;
        this.filePath = filePath;
        this.fileDir = fileDir;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.createdAt = createdAt;
        this.username = username;
        this.fileUrl = fileUrl;
    }
}

