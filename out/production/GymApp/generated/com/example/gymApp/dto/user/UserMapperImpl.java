package com.example.gymApp.dto.user;

import com.example.gymApp.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-19T09:39:29+0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserLoginDto toUserLoginDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserLoginDto userLoginDto = new UserLoginDto();

        userLoginDto.setUsername( user.getUsername() );
        userLoginDto.setPassword( user.getPassword() );

        return userLoginDto;
    }

    @Override
    public User toUser(UserLoginDto userLoginDto) {
        if ( userLoginDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username( userLoginDto.getUsername() );
        user.password( userLoginDto.getPassword() );

        return user.build();
    }
}
