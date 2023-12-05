package com.example.studentapp.repository;

import com.example.studentapp.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // nadmiarowo, bo JpaRepository samo w sobie już jest beanem, ale stosujemy dla czytelności
public interface TaskRepository extends JpaRepository<TaskModel, Long> {
}
