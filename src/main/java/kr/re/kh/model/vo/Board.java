package kr.re.kh.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Board {

    private Long id;
    private String code;

    private String codeName;
    private String title;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endDate;
    private String regId;
    private String modId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<UploadFile> fileList;

    @Builder
    public Board(Long id, String code, String codeName, String title, String content, Date startDate, Date endDate, String regId, String modId, LocalDateTime createdAt, LocalDateTime updatedAt, List<UploadFile> fileList) {
        this.id = id;
        this.code = code;
        this.codeName = codeName;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.regId = regId;
        this.modId = modId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.fileList = fileList;
    }
}
