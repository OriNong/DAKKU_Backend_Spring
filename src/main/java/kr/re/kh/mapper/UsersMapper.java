package kr.re.kh.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.Optional;

@Mapper
public interface UsersMapper {

    HashMap<String, Object> selectFileNameByUsername(String username);

}
