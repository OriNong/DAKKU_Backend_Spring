package kr.re.kh.model.payload.request;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SpRequest {

    private String cmd;
    private String xmlText;
    private String erMsg;
    private String rsMsg;

}
