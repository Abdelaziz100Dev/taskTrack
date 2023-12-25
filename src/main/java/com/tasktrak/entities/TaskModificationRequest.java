package com.tasktrak.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TaskModificationRequest {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @OneToOne
        private Task originalTask;

        @OneToOne
        private Task newTask;

        @ManyToOne
        private User requestingUser;

        private boolean managerApproval;
        private LocalDateTime requestDate;


}
