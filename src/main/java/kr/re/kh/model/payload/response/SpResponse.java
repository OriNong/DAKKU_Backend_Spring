package kr.re.kh.model.payload.response;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Slf4j
public class SpResponse {

    private String rsMsg;
    private String erMsg;

    @Builder
    public SpResponse(String rsMsg, String erMsg) {
        this.rsMsg = rsMsg;
        this.erMsg = erMsg;
    }

}
