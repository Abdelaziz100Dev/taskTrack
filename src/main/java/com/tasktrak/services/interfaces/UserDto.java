package com.tasktrak.services.interfaces;

import java.io.Serializable;

/**
 * DTO for {@link com.tasktrak.entities.User}
 */
public record UserDto(Long id, String username, boolean isManager) implements Serializable {
}