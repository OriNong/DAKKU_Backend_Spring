package kr.re.kh.repository;

import kr.re.kh.model.vo.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
    Optional<UploadFile> findByUsername(String username);
    void deleteByUsername(String username);
}