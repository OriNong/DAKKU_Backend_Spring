package kr.re.kh.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SseMapper {
    void createSseInfo(Long userID);
    void userUpdate(Long userID);
    Long userCheck(Long userID);
}
