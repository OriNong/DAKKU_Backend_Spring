package kr.re.kh.service;

import kr.re.kh.mapper.WeatherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherIconService {

    @Autowired
    private WeatherMapper weatherMapper;

    /**
     * 날씨 상태 코드에 맞는 이모티콘을 DB에서 조회
     *
     * @param weatherId 날씨 고유 ID
     * @return WeatherVO 날씨 상태에 맞는 이모티콘
     */
    public String getWeatherIconById(int weatherId) {
        return weatherMapper.selectWeatherIconByWeatherId(weatherId);  // DB에서 날씨 정보 조회
    }

    public String getWeatherIconByStatus(int weatherId) {
        return weatherMapper.selectWeatherIconByWeatherId(weatherId);
    }

    public String findByWeatherStatus(String weatherStatus) {
        return "";
    }
}
