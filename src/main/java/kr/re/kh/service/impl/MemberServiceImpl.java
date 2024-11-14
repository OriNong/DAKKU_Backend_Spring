package kr.re.kh.service.impl;



import kr.re.kh.mapper.Membermapper;
import kr.re.kh.model.payload.request.SpRequest;
import kr.re.kh.model.payload.response.SpResponse;
import kr.re.kh.model.vo.MemberVO;
import kr.re.kh.service.FileMapService;
import kr.re.kh.service.MemberService;
import kr.re.kh.util.Util;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final Membermapper mapper;

    @Override
    public Optional<MemberVO> memberSelectByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public SpResponse save(SpRequest spRequest) {
        return null;
    }

    @Override
    public Object selectOne(Object o) {
        return null;
    }

    @Override
    public void update(Object o) {

    }

    /**
     * 비밀번호찾기(임시비번발급)
     * @param requestMap
     * @return
     */
    public String changePW(String username, String email) {


        return ("");
    }




//    public Object changePW(HashMap<String, String> requestMap) {
//        // HashMap에서 이메일과 사용자 아이디를 추출
//        String email = requestMap.get("email");
//        String username = requestMap.get("username");
//
//        // MemberVO 객체를 생성하고 setter로 값 설정
//        MemberVO memberVO = new MemberVO();
//        memberVO.setEmail(email);
//        memberVO.setUsername(username);
//
//        // 비밀번호 찾기 쿼리 실행
//        //Long userId = mapper.findUserId(memberVO);
//
//        HashMap<String, Object> responseMap = new HashMap<>();
//
//        // 계정이 없으면 오류 메시지 반환
////        if (username == null) {
////            responseMap.put("result", false);
////            responseMap.put("message", "계정을 찾을 수 없습니다.");
////            return responseMap;
////        }
//
//        // 계정이 존재하면 임시 비밀번호 생성
//        String randomPw = Util.randomString(6);
//
//        // 비밀번호 업데이트
//        memberVO.setPassword(randomPw);
//
////        mapper.updatePW(memberVO);
//
//        // 성공 메시지와 임시 비밀번호 반환
//        responseMap.put("result", true);
//        responseMap.put("message", "임시 비밀번호는 " + randomPw + "입니다. 반드시 복사해주세요.");
//
//        return responseMap;
//    }


}
