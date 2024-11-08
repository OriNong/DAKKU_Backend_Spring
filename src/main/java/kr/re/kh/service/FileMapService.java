package kr.re.kh.service;

import kr.re.kh.model.vo.FileMap;

public interface FileMapService {

    void insertFileMap(FileMap fileMap);

    Boolean checkFileMap(FileMap fileMap);

}
