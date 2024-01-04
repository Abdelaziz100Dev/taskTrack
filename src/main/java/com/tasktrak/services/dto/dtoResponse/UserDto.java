package com.tasktrak.services.dto.dtoResponse;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link com.tasktrak.entities.User}
 */


//@Getter
//@Setter
public record UserDto(Long id, String username, boolean isManager) implements Serializable {
}