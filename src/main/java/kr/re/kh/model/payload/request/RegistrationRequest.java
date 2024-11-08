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

import kr.re.kh.validation.annotation.NullOrNotBlank;
import lombok.*;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @NullOrNotBlank(message = "Registration username can be null but not blank")
    private String username;

    @NullOrNotBlank(message = "Registration email can be null but not blank")
    private String email;

    @NotNull(message = "Registration password cannot be null")
    private String password;

    private String name;
}
