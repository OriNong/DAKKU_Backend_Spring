package kr.re.kh.mapper;

import kr.re.kh.model.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public interface Membermapper {

    /**
     * 아이디와 이메일로 사용자의 PK값 얻어내기
     * @param memberVO
     * @return
     */
    Long findUserId(MemberVO memberVO);

    /**
     * PK값으로 비밀번호 변경
     * @param memberVO
     */
    void updatePW(MemberVO memberVO);

}
