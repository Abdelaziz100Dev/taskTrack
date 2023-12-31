package com.tasktrak.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private Integer tokensForTaskModification;
    private Integer tokensForTaskDeletion;
    private Integer tokensUsed;

    private LocalDate lastModificationRequsetDate;
    private LocalDate lastDeletionDate;

    private Boolean isManager;


    @ManyToOne
    @JoinColumn(name="manager_id", nullable=true)
    private User manager;

    @OneToMany(mappedBy="manager")
    private List<User> users;

    @OneToMany(mappedBy = "assignedToUser")
    private List<Task> hisTasks;

    public void updateModificationRequestDate() {
        this.lastModificationRequsetDate = LocalDate.now();
    }
    public void updateDeletionRequestDate() {
        this.lastDeletionDate = LocalDate.now();
    }

    public void decrementTokensForTaskModification() {
        this.tokensForTaskModification--;
        this.tokensUsed++;
    }
    public void decrementTokensForTaskDeletion() {
        this.tokensForTaskDeletion--;
        this.tokensUsed++;
    }

    public boolean canMakeRequestForModification() {
        LocalDate today = LocalDate.now();

        if (!today.equals(lastModificationRequsetDate)) {
            tokensForTaskModification = 2;
            lastModificationRequsetDate = today;
        }
        // Check if the user can make another modification today
            return tokensForTaskModification > 0;
    }

    public boolean canDeleteTask() {
        LocalDate today = LocalDate.now();

        if (today.isAfter(lastDeletionDate.plusMonths(1))) {
            tokensForTaskDeletion = 1;
            lastDeletionDate = today;
        }
        // Check if the user can make another modification today
        return tokensForTaskDeletion > 0;
    }
    public void doubleTheModificationTokensStock(){
        this.setTokensForTaskModification(2);

    }
@JsonManagedReference
    public List<Task> getHisTasks() {
        return hisTasks;
    }

    @JsonBackReference
    public User getManager() {
        return manager;
    }
    @JsonManagedReference
    public List<User> getUsers() {
        return users;
    }

    public boolean isManager() {
        return isManager;
    }

}
