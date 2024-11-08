package kr.re.kh.mapper;

import kr.re.kh.model.vo.FileMap;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapMapper {

    void insertFileMap(FileMap fileMap);

    Boolean checkFileMap(FileMap fileMap);

}
