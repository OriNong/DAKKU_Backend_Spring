package kr.re.kh.util;

import kr.re.kh.model.User;
import kr.re.kh.model.payload.response.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ModelMapper {

    public static UserListResponse mapUserToUserResponse(User user) {
        UserListResponse userResponse = new UserListResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setUserId(user.getId());
        userResponse.setActive(user.getActive());
        userResponse.setEmailActive(user.getEmailVerified());
        userResponse.setCreatedAt(user.getCreatedAt());

        return  userResponse;
    }

    public static List<UserResponse> mapUserListToUserResponseList(List<User> list) {
        List<UserResponse> userResponseList = new ArrayList<>();
        for (User user : list) {
            UserResponse userResponse = new UserResponse();
            userResponse.setUsername(user.getUsername());
            userResponse.setEmail(user.getEmail());
            userResponse.setId(user.getId());
            userResponse.setActive(user.getActive());
            userResponse.setName(user.getName());
            userResponseList.add(userResponse);
        }
        return  userResponseList;
    }
}
