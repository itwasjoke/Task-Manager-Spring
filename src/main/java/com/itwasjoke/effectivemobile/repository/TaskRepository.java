package com.itwasjoke.effectivemobile.repository;

import com.itwasjoke.effectivemobile.customEnum.PriorityTask;
import com.itwasjoke.effectivemobile.customEnum.StatusTask;
import com.itwasjoke.effectivemobile.entity.Task;
import com.itwasjoke.effectivemobile.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findTaskById(Long id);
    Page<Task> findTasksByAuthorAndStatusAndPriority(
            User user,
            StatusTask statusTask,
            PriorityTask priorityTask,
            Pageable pageable
    );
    Page<Task> findTasksByExecutorAndStatusAndPriority(
            User user,
            StatusTask statusTask,
            PriorityTask priorityTask,
            Pageable pageable
    );
}
