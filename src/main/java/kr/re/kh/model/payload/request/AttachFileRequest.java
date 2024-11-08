package kr.re.kh.model.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class AttachFileRequest {
    private MultipartFile file;
    private String fileTarget;
}
