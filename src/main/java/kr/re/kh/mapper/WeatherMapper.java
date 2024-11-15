package kr.re.kh.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WeatherMapper {
    // 날씨 상태 코드에 맞는 이모티콘 반환
    String selectWeatherIconByWeatherId(int weatherId);

    String getWeatherById(int weatherId);

    // 날씨 상태 코드와 이모티콘을 DB에 저장
    void insertWeatherIcon(int weatherId, String weatherIcon);
}
