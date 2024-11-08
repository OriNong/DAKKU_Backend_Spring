package kr.re.kh.service.impl;

import kr.re.kh.mapper.FileMapMapper;
import kr.re.kh.model.vo.FileMap;
import kr.re.kh.service.FileMapService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FileMapServiceImpl implements FileMapService {

    private final FileMapMapper fileMapMapper;

    /**
     * fileMap 저장
     * @param fileMap
     */
    @Override
    public void insertFileMap(FileMap fileMap) {
        fileMapMapper.insertFileMap(fileMap);
    }

    /**
     * fileMap 존재 여부
     * @param fileMap
     * @return
     */
    @Override
    public Boolean checkFileMap(FileMap fileMap) {
        return fileMapMapper.checkFileMap(fileMap);
    }

}
