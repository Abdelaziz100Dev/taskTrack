package com.tasktrak.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tasktrak.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
//import org.springframework.data.annotation.Id;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private LocalDate creationDate;
    private LocalDate startDate;
    private LocalDate dueDate;

    private boolean completed;
    private boolean replaced;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ElementCollection
    private Set<String> tags;

    @ManyToOne
    private User assignedByUser;

    @ManyToOne
    @JoinColumn(name = "assignedToUser_id")
    private User assignedToUser;

    @ManyToOne
    private User createdByUser;

    public boolean isOverdue(){
        LocalDate today = LocalDate.now();
        return today.isAfter(this.dueDate) && !completed;
    }
    public void setNotComplete(){
        this.completed = false;

    }

    @JsonBackReference
    public User getAssignedByUser() {
        return assignedByUser;
    }
    @JsonBackReference
    public User getAssignedToUser() {
        return assignedToUser;
    }
    @JsonManagedReference
    public Set<String> getTags() {
        return tags;
    }
}