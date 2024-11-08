/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kr.re.kh.model.payload.request;

import kr.re.kh.model.payload.DeviceInfo;
import kr.re.kh.validation.annotation.NullOrNotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {

    @NullOrNotBlank(message = "아이디는 필수 항목입니다.")
    private String username;

    @NotNull(message = "비밀번호는 필수 항목입니다.")
    private String password;

    @Valid
    @NotNull(message = "장치정보는 필수 항목입니다.")
    private DeviceInfo deviceInfo;

}
