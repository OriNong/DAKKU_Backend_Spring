package kr.re.kh.controller.auth;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import kr.re.kh.exception.*;
import kr.re.kh.model.CustomUserDetails;
import kr.re.kh.model.payload.request.*;
import kr.re.kh.model.payload.response.ApiResponse;
import kr.re.kh.model.payload.response.JwtAuthenticationResponse;
import kr.re.kh.model.token.RefreshToken;
import kr.re.kh.security.JwtTokenProvider;
import kr.re.kh.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider tokenProvider;

    /**
     * 이메일 사용여부 확인
     */
    @ApiOperation(value = "이메일 사용여부 확인")
    @ApiImplicitParam(name = "email", dataType = "String", required = true)
    @GetMapping("/check/email")
    public ResponseEntity<?> checkEmailInUse(@RequestParam("email") String email) {
        boolean emailExists = authService.emailAlreadyExists(email);
        return ResponseEntity.ok(new ApiResponse(true, emailExists ? "이미 사용중인 이메일입니다." : ""));
    }

    /**
     * username 사용여부 확인
     */
    @ApiOperation(value = "아이디 사용여부 확인")
    @ApiImplicitParam(name = "username", dataType = "String", required = true)
    @GetMapping("/check/username")
    public ResponseEntity<?> checkUsernameInUse(@RequestParam(
            "username") String username) {
        boolean usernameExists = authService.usernameAlreadyExists(username);
        return ResponseEntity.ok(new ApiResponse(true, usernameExists ? "이미 사용중인 아이디입니다.": ""));
    }


    /**
     * 로그인 성공시 access token, refresh token 반환
     */
    @ApiOperation(value = "로그인")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", dataType = "String", required = true),
            @ApiImplicitParam(name = "password", dataType = "String", required = true),
            @ApiImplicitParam(name = "deviceInfo", dataType = "DeviceInfo", required = true)
    })
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        
        log.info("login user >> " + loginRequest.getPassword() + " // " + loginRequest.getUsername());

        Authentication authentication = authService.authenticateUser(loginRequest)
                .orElseThrow(() -> new UserLoginException("Couldn't login user [" + loginRequest + "]"));

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        log.info("Logged in User returned [API]: " + customUserDetails.getUsername());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authService.createAndPersistRefreshTokenForDevice(authentication, loginRequest)
                .map(RefreshToken::getToken)
                .map(refreshToken -> {
                    String jwtToken = authService.generateToken(customUserDetails);
                    return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken, refreshToken, tokenProvider.getExpiryDuration()));
                })
                .orElseThrow(() -> new UserLoginException("Couldn't create refresh token for: [" + loginRequest + "]"));
    }

    /**
     * 특정 장치에 대한 refresh token 을 사용하여 만료된 jwt token 을 갱신 후 새로운 token 을 반환
     */
    @ApiOperation(value = "리프레시 토큰")
    @ApiImplicitParam(name = "refreshToken", dataType = "String", required = true)
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshJwtToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {

        log.info(tokenRefreshRequest.toString());

        return authService.refreshJwtToken(tokenRefreshRequest)
                .map(updatedToken -> {
                    String refreshToken = tokenRefreshRequest.getRefreshToken();
                    log.info("Created new Jwt Auth token: " + updatedToken);
                    return ResponseEntity.ok(new JwtAuthenticationResponse(updatedToken, refreshToken, tokenProvider.getExpiryDuration()));
                })
                .orElseThrow(() -> new TokenRefreshException(tokenRefreshRequest.getRefreshToken(), "토큰 갱신 중 오류가 발생했습니다. 다시 로그인 해 주세요."));
    }

    /**
     * 회원 가입
     * @param request
     * @return
     */
    @ApiOperation(value = "회원가입")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", dataType = "String", required = true),
            @ApiImplicitParam(name = "email", dataType = "String", required = true),
            @ApiImplicitParam(name = "password", dataType = "String", required = true),
            @ApiImplicitParam(name = "name", dataType = "String", required = true)
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest request) {
        log.info(request.toString());
        return authService.registerUser(request).map(user -> {
            return ResponseEntity.ok(new ApiResponse(true, "등록되었습니다."));
        }).orElseThrow(() -> new UserRegistrationException(request.getUsername(), "가입오류"));
    }

}
