package kr.re.kh.model.vo;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DiaryVO {
    private int diaryId;            // 일기 고유 id
    private int memberId;           // 작성자 id
    private String diaryTitle;      // 일기 제목
    private String diaryContent;    // 일기 본문
    private Date diaryCrtDate;      // 일기 생성 일자
    private Date diartyUpdDate;     // 일기 수정 일자
    private boolean isPublic;       // 공개 여부
    private int weatherId;          // 날씨 고유 id
    private int diaryViews;         // 일기 조회 수
    private int diaryLikes;         // 일기 좋아요 수
}
