package com.tasktrak.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;
//import org.springframework.data.annotation.Id;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDate creationDate;
    private LocalDate dueDate;
    private boolean completed;

    @ElementCollection
    private Set<String> tags;

    @ManyToOne
    private User assignedUser;

    @ManyToOne
    private User createdByUser;
}