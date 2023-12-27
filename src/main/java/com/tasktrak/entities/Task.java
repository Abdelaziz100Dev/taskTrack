package com.tasktrak.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;
//import org.springframework.data.annotation.Id;

@Entity
@Data
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
    private LocalDate dueDate;
    private boolean completed;
    private boolean replaced;

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
}