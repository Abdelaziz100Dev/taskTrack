package com.tasktrak.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private int tokensForTaskModification;
    private int tokensForTaskDeletion;

    private LocalDate lastModificationRequsetDate;
    private LocalDate lastDeletionDate;

    private boolean isManager;
    @ManyToOne
    private User manager;

    @OneToMany(mappedBy = "assignedToUser")
    private List<Task> tasksAssigned;

    public void updateModificationRequestDate() {
        this.lastModificationRequsetDate = LocalDate.now();
    }
    public void updateDeletionRequestDate() {
        this.lastDeletionDate = LocalDate.now();
    }

    public void decrementTokensForTaskModification() {
        this.tokensForTaskModification--;
    }
    public void decrementTokensForTaskDeletion() {
        this.tokensForTaskDeletion--;
    }

    public boolean canMakeRequestForModification() {
        LocalDate today = LocalDate.now();

        if (!today.equals(lastModificationRequsetDate)) {
            tokensForTaskModification = 2;
            lastModificationRequsetDate = today;
        }
    }

    public boolean canDeleteTask() {
        LocalDate today = LocalDate.now();

        if (!today.isAfter(lastDeletionDate.plusMonths(1))) {
            tokensForTaskDeletion = 1;
            lastDeletionDate = today;
        }
        // Check if the user can make another modification today
        return tokensForTaskDeletion > 0;
    }

}
