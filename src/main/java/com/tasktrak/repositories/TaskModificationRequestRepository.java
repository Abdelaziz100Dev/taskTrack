package com.tasktrak.repositories;

import com.tasktrak.entities.TaskModificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskModificationRequestRepository extends JpaRepository<TaskModificationRequest, Long> {
}
