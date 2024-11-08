package kr.re.kh.model.payload.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeleteRequest {

    private Long[] id;
    private String fileTarget;

}
