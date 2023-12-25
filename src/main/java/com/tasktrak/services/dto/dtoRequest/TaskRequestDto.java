package com.tasktrak.services.dto.dtoRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link com.tasktrak.entities.Task}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDto{
    private String title;
    private  String description;
    private  LocalDate creationDate;
    private  LocalDate dueDate;
    private  Boolean completed;
    private Set<String> tags;
}
// json for this class:
// {
//   "title": "string",
//   "description": "string",
//   "creationDate": "2021-07-07",
//   "dueDate": "2021-07-07",
//   "completed": true,
//   "tags": [
//     "string"
//   ]
// }
