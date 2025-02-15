package kr.re.kh.service;

import kr.re.kh.model.payload.request.SpRequest;
import kr.re.kh.model.payload.response.SpResponse;
import kr.re.kh.model.vo.MemberVO;

import java.util.Optional;

public interface MemberService<E> {

    Optional<MemberVO> memberSelectByUsername(String username);

    SpResponse save(SpRequest spRequest);

    E selectOne(E e);

    void update(E e);

}
