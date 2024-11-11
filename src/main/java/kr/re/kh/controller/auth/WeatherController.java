package kr.re.kh.controller.auth;

import kr.re.kh.service.WeatherIconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherIconService weatherService;

    // 위도 경도 파악이 필요하면 아래 url 호출
    // http://api.openweathermap.org/geo/1.0/direct?q=Seoul&limit=5&appid=e2799674d5b13024688e4a3159a6829d
    // 위도 경도 기준 날씨 정보 url
    // http://api.openweathermap.org/data/2.5/weather?lat=37.5666791&lon=126.9782914&appid=e2799674d5b13024688e4a3159a6829d
    // 날씨 상태 코드에 맞는 이모티콘을 반환하는 API
    @GetMapping("/weatherIcon")
    public String getWeatherIcon(@RequestParam("weatherId") int weatherId) {

        return weatherService.getWeatherIconByStatus(weatherId);
    }
}