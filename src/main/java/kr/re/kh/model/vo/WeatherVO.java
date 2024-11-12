package kr.re.kh.model.vo;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WeatherVO {
    private int weatherId;       // 날씨 고유 id
    private String weatherStatus; // 날씨 상태
    private String weatherIcon; // 이모티콘
}
