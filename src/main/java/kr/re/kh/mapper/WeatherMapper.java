package kr.re.kh.mapper;

import kr.re.kh.model.vo.WeatherVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WeatherMapper {
    String selectWeatherIconByWeatherId(int weatherId);

    String getWeatherById(int weatherId);

    WeatherVO findByWeatherStatus(String weatherStatus);
}
