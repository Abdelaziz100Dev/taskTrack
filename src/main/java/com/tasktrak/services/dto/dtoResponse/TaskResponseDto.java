package com.tasktrak.services.dto.dtoResponce;

import com.tasktrak.services.interfaces.UserDto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link com.tasktrak.entities.Task}
 */
public record TaskResponceDto(Long id, String title, String description, LocalDate creationDate, LocalDate dueDate,
                              boolean completed, Set<String> tags, Long assignedUserResponceDtoId,
                              String assignedUserResponceDtoUsername, boolean assignedUserResponceDtoIsManager,
                              UserDto createdByUser) implements Serializable {
}