package kr.re.kh.model.vo;

import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WeatherVO {
    private int weaderId;       // 날씨 고유 id
    // 이모티콘 형식에 맞는 필드 값 추가 (weatherIcon)
}
