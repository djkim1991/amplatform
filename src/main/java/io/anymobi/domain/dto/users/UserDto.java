package io.anymobi.domain.dto.users;

import io.anymobi.common.annotation.PasswordMatches;
import io.anymobi.common.annotation.ValidEmail;
import io.anymobi.common.annotation.ValidPassword;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PasswordMatches
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    @NotNull
    @Size(min = 1, message = "{Size.userDto.username}")
    private String username;

    @NotNull
    @Size(min = 1, message = "{Size.userDto.firstName}")
    private String firstName;

    @NotNull
    @Size(min = 1, message = "{Size.userDto.lastName}")
    private String lastName;

    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 1)
    private String matchingPassword;

    @ValidEmail
    @NotNull
    @Size(min = 1, message = "{Size.userDto.email}")
    private String email;

    private boolean isUsing2FA;
    private Integer role;

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("UserDto [firstName=").append(firstName).append(", lastName=").append(lastName).append(", password=").append(password).append(", matchingPassword=").append(matchingPassword).append(", email=").append(email).append(", isUsing2FA=")
                .append(isUsing2FA).append(", role=").append(role).append("]");
        return builder.toString();
    }

}
