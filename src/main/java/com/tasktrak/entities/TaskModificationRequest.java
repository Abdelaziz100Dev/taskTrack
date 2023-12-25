package com.tasktrak.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
