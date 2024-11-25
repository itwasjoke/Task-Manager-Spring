package com.itwasjoke.effectivemobile.DTO.mapper;


import com.itwasjoke.effectivemobile.DTO.UserRequestDTO;
import com.itwasjoke.effectivemobile.DTO.UserResponseDTO;
import com.itwasjoke.effectivemobile.customEnum.Role;
import com.itwasjoke.effectivemobile.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserMapper {
    User userRequestToUser(UserRequestDTO userRequestDTO);

    @Mapping(target = "role", source = "role")
    UserResponseDTO userToUserResponse(User user, String role);
}
