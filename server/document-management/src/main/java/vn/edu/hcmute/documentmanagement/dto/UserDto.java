package vn.edu.hcmute.documentmanagement.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;
import vn.edu.hcmute.documentmanagement.model.Document;
import vn.edu.hcmute.documentmanagement.model.Ministry;
import vn.edu.hcmute.documentmanagement.model.Role;
import vn.edu.hcmute.documentmanagement.model.User;
import vn.edu.hcmute.documentmanagement.util.CustomDate;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class UserDto {

    @Min(value=0)
    private long id;

    @NotBlank(message = "full name is required")
    private String fullName;

    @Past(message = "birthday must be less than or equal to the current datetime")
    @NotNull
    private Date birthday;

    @Email(message = "email must follow the standard conventions")
    private String email;

    @Size(min = 5, max = 20, message = "Username should be 6-20 characters long")
    @Column(name = "username", unique = true)
    private String username;


    @NotBlank(message = "Password is mandatory")
    @Min(value = 8, message = "Password should be at least 8 characters long")
    @Column(name = "pw")
    private String password;

    @NotNull(message = "user must belongs to a ministry")
    private String ministry;

    @Min(value = 1, message = "user must have at least one role")
    private long roleId;

    public static UserDto of(@NotNull User user) {
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .birthday(user.getBirthday())
                .email(user.getEmail())
                .ministry(user.getMinistry().getName())
                .roleId(user.getId()).build();
        return userDto;
    }

    public static List<UserDto> of(List<User> users){
        List<UserDto> userDtos = users.stream().map(UserDto::of).toList();
        return userDtos;
    }
    public static User toUser(UserDto userDto, Role role, Ministry ministry) {
        User doc = User.builder()
                .id(userDto.getId())
                .fullName(userDto.getFullName())
                .birthday(userDto.getBirthday())
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .build();
        doc.addMinistry(ministry);
        doc.addRole(role);
        return doc;
    }
}
