package com.example.gymApp.dto.user;

import com.example.gymApp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(source = "username", target = "username")
  @Mapping(source = "password", target = "password")
  UserLoginDto toUserLoginDto(User user);

  @Mapping(source = "username", target = "username")
  @Mapping(source = "password", target = "password")
  User toUser (UserLoginDto userLoginDto);


}
