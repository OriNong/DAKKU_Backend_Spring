package kr.re.kh.model.vo;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DiaryVO {
    private Long diaryId;                   // 일기 고유 id
    private Long memberId;                  // 작성자 id
    private String diaryTitle;              // 일기 제목
    private String diaryContent;            // 일기 본문
    private Boolean isPublic;               // 공개 여부
    private Long diaryViews;                // 일기 조회 수
    private Long diaryLikes;                // 일기 좋아요 수
    private String weatherIcon;             // 날씨 이모티콘
    private LocalDateTime diaryCrtDate;     // 일기 생성 일자
    private LocalDateTime diaryUpdDate;    // 일기 수정 일자

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }
}
